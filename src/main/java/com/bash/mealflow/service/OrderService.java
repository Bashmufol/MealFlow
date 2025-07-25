package com.bash.mealflow.service;

import com.bash.mealflow.model.Order;
import com.bash.mealflow.model.User;
import com.bash.mealflow.repository.MenuItemRespository;
import com.bash.mealflow.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    }

}
