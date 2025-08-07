package com.bash.mealflow.dto;

import com.bash.mealflow.model.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Format for Thymeleaf date input
    private LocalDate availableDate;
    private Boolean isAvailable;

    // Helper method to convert MenuItem to MenuItemDto
    public static MenuItemDto fromEntity(MenuItem menuItem) {
        if (menuItem == null) return null;
        return new MenuItemDto(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getAvailableDate(),
                menuItem.getIsAvailable()
        );
    }

    // Helper method to convert MenuItemDto to MenuItem (for saving/updating)
    public MenuItem toEntity() {
        com.bash.mealflow.model.MenuItem menuItem = new com.bash.mealflow.model.MenuItem();
        menuItem.setId(this.id);
        menuItem.setName(this.name);
        menuItem.setDescription(this.description);
        menuItem.setPrice(this.price);
        menuItem.setAvailableDate(this.availableDate);
        menuItem.setIsAvailable(this.isAvailable);
        return menuItem;
    }
}