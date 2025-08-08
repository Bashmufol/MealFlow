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
        List<Order> pendingOrders = orderService.getPendingOrders();
        model.addAttribute("pendingOrders", pendingOrders);
        List<Order> allOrders = orderService.getAllOrderForAdmin();
        model.addAttribute("allOrders", allOrders);
        return "admin-dashboard";
    }

    @GetMapping("/menu/new")
    public String showCreateMenuItemForm(Model model){
        model.addAttribute("menuItem", new MenuItemDto()); // Use DTO
        return "admin-menu-form";
    }

    @PostMapping("/menu/save")
    public String saveMenuitem(@ModelAttribute MenuItemDto menuItemDto, RedirectAttributes redirectAttributes){
        menuItemDto.setAvailableDate(LocalDate.now());
        // The service will handle mapping from DTO to entity
        menuItemService.saveMenuItem(menuItemDto);
        redirectAttributes.addFlashAttribute("successMessage", "Menu item saved successfully");
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/menu/edit/{id}") // Added for editing functionality
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
            // Set ID from path to DTO for consistent update logic
            menuItemDto.setId(id);
            menuItemDto.setAvailableDate(LocalDate.now());
            menuItemService.updateMenuItem(menuItemDto);
            redirectAttributes.addFlashAttribute("successMessage", "Menu item updated successfully");
        } catch(ResourceNotFoundException e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/menu/edit/" + id; // Redirect back to edit with error
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