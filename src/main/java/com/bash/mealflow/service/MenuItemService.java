package com.bash.mealflow.service;

import com.bash.mealflow.customException.ResourceNotFoundException;
import com.bash.mealflow.dto.MenuItemDto;
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
    // New method to get MenuItemDto
    public Optional<MenuItemDto> getMenuItemDtoById(Long menuItemId) {
        return menuItemRepository.findById(menuItemId).map(MenuItemDto::fromEntity);
    }

    // Save using DTO
    public MenuItem saveMenuItem(MenuItemDto menuItemDto) {
        MenuItem menuItem = menuItemDto.toEntity();
        if(menuItem.getAvailableDate() == null){
            menuItem.setAvailableDate(LocalDate.now());
        }
        if(menuItem.getIsAvailable() == null){
            menuItem.setIsAvailable(true); // Default to available
        }
        return menuItemRepository.save(menuItem);
    }

    // Update using DTO
    public MenuItem updateMenuItem(MenuItemDto menuItemDto) {
        Long menuItemId = menuItemDto.getId();
        Optional<MenuItem> menuItemOptional = menuItemRepository.findById(menuItemId);
        if(menuItemOptional.isPresent()){
            MenuItem existingMenuItem = menuItemOptional.get();
            existingMenuItem.setName(menuItemDto.getName());
            existingMenuItem.setDescription(menuItemDto.getDescription());
            existingMenuItem.setPrice(menuItemDto.getPrice());
            existingMenuItem.setAvailableDate(menuItemDto.getAvailableDate());
            existingMenuItem.setIsAvailable(menuItemDto.getIsAvailable());
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