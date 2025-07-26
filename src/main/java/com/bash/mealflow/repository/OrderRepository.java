package com.bash.mealflow.repository;

import com.bash.mealflow.model.Order;
import com.bash.mealflow.model.OrderStatus;
import com.bash.mealflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserOrderByOrderDateDesc(User user);

    // For canteen staff to view pending orders
    List<Order> findByStatusOrderByOrderDateAsc(OrderStatus status);
    // For admin to view all orders
    List<Order> findAllByOrderByOrderDateDesc();
}
