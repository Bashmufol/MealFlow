package com.bash.mealflow.controller;

import com.bash.mealflow.model.MenuItem;
import com.bash.mealflow.model.Order;
import com.bash.mealflow.model.User;
import com.bash.mealflow.service.MenuItemService;
import com.bash.mealflow.service.OrderService;
import com.bash.mealflow.service.UserPrincipal;
import com.bash.mealflow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MenuItemService menuItemService;
    private final OrderService orderService;

    @GetMapping("/dashboard")
    public String UserDashboard(@AuthenticationPrincipal UserPrincipal currentUserPrincipal, Model model){
        List<MenuItem> dailyMenu = menuItemService.getDailyMenuForUser();
        model.addAttribute("menuItems", dailyMenu);
        User currentUser = currentUserPrincipal.getUser();
        List<Order> orderHistory = orderService.getOrderHistoryForUser(currentUser);
        model.addAttribute("orderHistory", orderHistory);
        return "employee-dashboard";
    }

    @PostMapping("/place-order")
    public String placeOrder(@AuthenticationPrincipal UserPrincipal currentUserPrincipal,
                             @RequestParam Map<String, String> formData,
                             RedirectAttributes redirectAttributes){

    }

}
