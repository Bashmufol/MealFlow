package com.bash.mealflow.service;

import com.bash.mealflow.customException.ResourceNotFoundException;
import com.bash.mealflow.model.MenuItem;
import com.bash.mealflow.model.Order;
import com.bash.mealflow.model.OrderItem;
import com.bash.mealflow.model.User;
import com.bash.mealflow.repository.MenuItemRespository;
import com.bash.mealflow.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
        order.setStatus("ORDERED");
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

}
