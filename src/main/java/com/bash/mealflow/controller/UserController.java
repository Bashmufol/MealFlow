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

import java.util.HashMap;
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
        return "user-dashboard";
    }

    @PostMapping("/place-order")
    public String placeOrder(@AuthenticationPrincipal UserPrincipal currentUserPrincipal,
                             @RequestParam Map<String, String> formData,
                             RedirectAttributes redirectAttributes) {
        User currentUser = currentUserPrincipal.getUser();
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found");
            return "redirect:/user/dashboard";
        }

        Map<Long, Integer> itemQuantities = new HashMap<>();
        for (Map.Entry<String, String> entry : formData.entrySet()) {
            if (entry.getKey().startsWith("quantity_") && !entry.getValue().isEmpty()) {
                try {
                    Long menuItemId = Long.parseLong(entry.getKey().replace("quantity_", ""));
                    int quantity = Integer.parseInt(entry.getValue());
                    if(quantity>0){
                        itemQuantities.put(menuItemId, quantity);
                    }
                } catch(NumberFormatException e){
                    redirectAttributes.addFlashAttribute("errorMessage", "Invalid quantity for an item.");
                    return "redirect:/user/dashboard";
                }
            }
        }
        try{
            orderService.placeOrder(currentUser, itemQuantities);
            redirectAttributes.addFlashAttribute("successMessage", "Order placed successfully");
        } catch(Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Error placing order. " + e.getMessage());
        }
        return "redirect:/user/dashboard";
    }
}
