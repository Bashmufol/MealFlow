package com.bash.mealflow.service;

import com.bash.mealflow.customException.ResourceNotFoundException;
import com.bash.mealflow.model.MenuItem;
import com.bash.mealflow.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }
    public List<MenuItem> getDailyMenuForUser(){
        return menuItemRepository.findByAvailableDateAndIsAvailableTrue(LocalDate.now());
    }
    public List<MenuItem> getAllMenuItemForToday(){
        return menuItemRepository.findByAvailableDate(LocalDate.now());
    }
    public Optional<MenuItem> getMenuItemById(Long menuItemId) {
        return menuItemRepository.findById(menuItemId);
    }
    public MenuItem saveMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }
    public MenuItem updateMenuItem(Long menuItemId, MenuItem menuItem) {
        Optional<MenuItem> menuItemOptional = menuItemRepository.findById(menuItemId);
        if(menuItemOptional.isPresent()){
            MenuItem existingMenuItem = menuItemOptional.get();
            existingMenuItem.setName(menuItem.getName());
            existingMenuItem.setDescription(menuItem.getDescription());
            existingMenuItem.setPrice(menuItem.getPrice());
            existingMenuItem.setAvailableDate(menuItem.getAvailableDate());
            existingMenuItem.setIsAvailable(menuItem.getIsAvailable());
            return menuItemRepository.save(existingMenuItem);
        } else {
            throw new ResourceNotFoundException("Cannot find menu item with the id: " + menuItemId);
            }
    }

    public void deleteMenuItem(Long menuItemId) {
        if(menuItemRepository.existsById(menuItemId)) {
            menuItemRepository.deleteById(menuItemId);
        } else  {
            throw new ResourceNotFoundException("Cannot find menu item with the id: " + menuItemId);
        }
    }
}
