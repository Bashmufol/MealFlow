package com.bash.mealflow.service;

import com.bash.mealflow.customException.ResourceNotFoundException;
import com.bash.mealflow.model.*;
import com.bash.mealflow.repository.MenuItemRepository;
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
    private final MenuItemRepository menuItemRepository;

    @Transactional
    public void placeOrder(User user, Map<Long, Integer> itemQuantity) {
        if(itemQuantity == null || itemQuantity.isEmpty()) {
            throw new IllegalArgumentException("Order cannot be empty.");
        }
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDERED);
        BigDecimal totalAmount  = BigDecimal.ZERO;

        // Collect OrderItems before adding to order to ensure all menu items are valid and available
        List<OrderItem> orderItems = itemQuantity.entrySet().stream()
                .map(entry -> {
                    Long menuItemId = entry.getKey();
                    Integer quantity = entry.getValue();

                    MenuItem menuItem = menuItemRepository.findById(menuItemId)
                            .orElseThrow(() -> new ResourceNotFoundException("Menu Item not found with id: " + menuItemId));

                    if(!menuItem.getIsAvailable()){
                        throw new IllegalStateException("Menu Item '" + menuItem.getName() + "' is not currently available.");
                    }

                    OrderItem orderItem = new OrderItem();
                    orderItem.setMenuItem(menuItem);
                    orderItem.setQuantity(quantity);
                    orderItem.setPriceAtOrder(menuItem.getPrice()); // Price captured at order time
                    return orderItem;
                })
                .collect(Collectors.toList());

        // Add order items to the order and calculate total amount
        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
            totalAmount = totalAmount.add(orderItem.getPriceAtOrder().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        }

        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
    }

    public List<Order> getOrderHistoryForUser(User user) {
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }

    public List<Order> getAllOrderForAdmin(){
        return orderRepository.findAllByOrderByOrderDateDesc();
    }

    public List<Order> getPendingOrders(){
        // Fetch orders that are 'ORDERED' or 'IN_PROGRESS'
        List<Order> ordered = orderRepository.findByStatusOrderByOrderDateAsc(OrderStatus.ORDERED);
        List<Order> inProgress = orderRepository.findByStatusOrderByOrderDateAsc(OrderStatus.IN_PROGRESS);
        ordered.addAll(inProgress);
        // Ensure consistent sorting
        return ordered.stream()
                .sorted((o1, o2) -> o1.getOrderDate().compareTo(o2.getOrderDate()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus newStatus){
        orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus(newStatus);
                    return orderRepository.save(order);
                }).orElseThrow(() -> new ResourceNotFoundException("Order Not Found with id: " + orderId));
    }

    public Optional<Order> getOrderById(Long orderId){
        return orderRepository.findById(orderId);
    }

    @Transactional
    public void cancelOrder(Long orderId, User user) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You are not authorized to cancel this order.");
        }

        if (order.getStatus() != OrderStatus.ORDERED) {
            throw new IllegalStateException("Order cannot be cancelled. It is already " + order.getStatus());
        }

        // If the order is 'ORDERED' and belongs to the user, delete it
        orderRepository.delete(order);
    }
}