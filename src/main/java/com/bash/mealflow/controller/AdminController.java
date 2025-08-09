package com.bash.mealflow.controller;

import com.bash.mealflow.customException.ResourceNotFoundException;
import com.bash.mealflow.dto.MenuItemDto;
import com.bash.mealflow.model.MenuItem;
import com.bash.mealflow.model.Order;
import com.bash.mealflow.model.OrderStatus;
import com.bash.mealflow.service.MenuItemService;
import com.bash.mealflow.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final MenuItemService menuItemService;
    private final OrderService orderService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model){
        List<MenuItem> menuItems = menuItemService.getAllMenuItemForToday();
        model.addAttribute("menuItems", menuItems);

        // Fetch all orders and then filter for pending (non-completed) orders for display.
        List<Order> allOrders = orderService.getAllOrderForAdmin();
        List<Order> pendingOrders = allOrders.stream()
                .filter(order -> order.getStatus() != OrderStatus.COMPLETED)
                .collect(Collectors.toList());

        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("allOrders", allOrders); // All orders for historical view.

        return "admin-dashboard";
    }

    @GetMapping("/menu/new")
    public String showCreateMenuItemForm(Model model){
        model.addAttribute("menuItem", new MenuItemDto());
        return "admin-menu-form";
    }

    @PostMapping("/menu/save")
    public String saveMenuitem(@ModelAttribute MenuItemDto menuItemDto, RedirectAttributes redirectAttributes){
        menuItemDto.setAvailableDate(LocalDate.now());
        menuItemService.saveMenuItem(menuItemDto);
        redirectAttributes.addFlashAttribute("successMessage", "Menu item saved successfully");
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/menu/edit/{id}")
    public String showEditMenuItemForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            MenuItemDto menuItemDto = menuItemService.getMenuItemDtoById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu Item not found with id: " + id));
            model.addAttribute("menuItem", menuItemDto);
            return "admin-menu-form";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/dashboard";
        }
    }

    @PostMapping("/menu/update/{id}")
    public String updateMenuItem(@PathVariable Long id, @ModelAttribute MenuItemDto menuItemDto, RedirectAttributes redirectAttributes){
        try{
            menuItemDto.setId(id);
            // Automatically set available date to current day upon update.
            menuItemDto.setAvailableDate(LocalDate.now());
            menuItemService.updateMenuItem(menuItemDto);
            redirectAttributes.addFlashAttribute("successMessage", "Menu item updated successfully");
        } catch(ResourceNotFoundException e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/menu/edit/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating menu item: " + e.getMessage());
            return "redirect:/admin/menu/edit/" + id;
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/menu/delete/{id}")
    public String deleteMenuItem(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            menuItemService.deleteMenuItem(id);
            redirectAttributes.addFlashAttribute("successMessage", "Menu item deleted successfully!");
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/orders/update-status/{orderId}")
    public String updateOrderStatus(@PathVariable Long orderId,
                                    @RequestParam OrderStatus status,
                                    RedirectAttributes redirectAttributes){
        try{
            orderService.updateOrderStatus(orderId, status);
            redirectAttributes.addFlashAttribute("successMessage", "Order status updated successfully!");
        } catch(ResourceNotFoundException e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }
}