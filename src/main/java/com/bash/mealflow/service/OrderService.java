package com.bash.mealflow.service;

import com.bash.mealflow.repository.MenuItemRespository;
import com.bash.mealflow.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MenuItemRespository menuItemRespository;

}
