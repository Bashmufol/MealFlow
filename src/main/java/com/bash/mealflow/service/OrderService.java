package com.bash.mealflow.service;

import com.bash.mealflow.customException.ResourceNotFoundException;
import com.bash.mealflow.model.*;
import com.bash.mealflow.repository.MenuItemRespository;
import com.bash.mealflow.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MenuItemRespository menuItemRespository;

    @Transactional
    public Order placeOrder(User user, Map<Long, Integer> itemQuantity) {
        if(itemQuantity == null || itemQuantity.isEmpty()) {
            throw new IllegalArgumentException("Order cannot be empty");
        }
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDERED);
        BigDecimal totalAmount  = BigDecimal.ZERO;

        for(Map.Entry<Long, Integer> entry : itemQuantity.entrySet()) {
            Long menuItemId = entry.getKey();
            Integer quantity = entry.getValue();

            MenuItem menuItem = menuItemRespository.findById(menuItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu Item not found with id: " + menuItemId));

            if(!menuItem.isAvailable()){
                throw new IllegalStateException("Menu Item " + menuItem.getName() + " is not currently available");
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(quantity);
            orderItem.setOrder(order);
            orderItem.setPriceAtOrder(menuItem.getPrice());
            totalAmount = totalAmount.add(menuItem.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }
        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }

    public List<Order> getOrderHistoryForUser(User user) {
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }

    public List<Order> getAllOrderForAdmin(){
        return orderRepository.findAllByOrderByOrderDateDesc();
    }
    public List<Order> getPendingOrders(){
//        Fetch orders that are 'ORDERED' or 'IN_PROGRESS'
        List<Order> ordered = orderRepository.findByStatusOrderByOrderDateAsc(OrderStatus.ORDERED);
        List<Order> inProgress = orderRepository.findByStatusOrderByOrderDateAsc(OrderStatus.IN_PROGRESS);
        ordered.addAll(inProgress);
        return ordered.stream()
                .sorted((o1, o2) -> o1.getOrderDate().compareTo(o2.getOrderDate()))
                .collect(Collectors.toList());
    }

    public Order updateOrderStatus(Long orderId, OrderStatus newStatus){
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus(newStatus);
                    return orderRepository.save(order);
                }).orElseThrow(() -> new ResourceNotFoundException("Order Not Found with id: " + orderId));

    }

    public Optional<Order> getOrderById(Long orderId){
        return orderRepository.findById(orderId);
    }

}
