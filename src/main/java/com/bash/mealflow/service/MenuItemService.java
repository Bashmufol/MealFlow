package com.bash.mealflow.service;

import com.bash.mealflow.model.MenuItem;
import com.bash.mealflow.repository.MenuItemRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuItemService {
    private final MenuItemRespository menuItemRespository;

    public List<MenuItem> getAllMenuItems() {
        return menuItemRespository.findAll();
    }
    public List<MenuItem> getDailyMenuForEmployee(){
        return menuItemRespository.findByAvailableDateAndIsAvailableTrue(LocalDate.now());
    }
    public List<MenuItem> getAllMenuItemForToday(){
        return menuItemRespository.findByAvailableDate(LocalDate.now());
    }
    public Optional<MenuItem> getMenuItemById(Long menuItemId) {
        return menuItemRespository.findById(menuItemId);
    }
    public MenuItem saveMenuItem(MenuItem menuItem) {
        return menuItemRespository.save(menuItem);
    }
    public MenuItem updateMenuItem(Long menuItemId, MenuItem menuItem) {
        Optional<MenuItem> menuItemOptional = menuItemRespository.findById(menuItemId);
        if(menuItemOptional.isPresent()){
            MenuItem existingMenuItem = menuItemOptional.get();
            existingMenuItem.setName(menuItem.getName());
            existingMenuItem.setDescription(menuItem.getDescription());
            existingMenuItem.setPrice(menuItem.getPrice());
            existingMenuItem.setAvailableDate(menuItem.getAvailableDate());
            existingMenuItem.setAvailable(menuItem.isAvailable());
        } else throw new ResourceNotFoundException

    }
}
