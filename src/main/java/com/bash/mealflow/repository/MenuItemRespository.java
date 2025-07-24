package com.bash.mealflow.repository;

import com.bash.mealflow.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MenuItemRespository extends JpaRepository<MenuItem, Long> {
    // For Employee to see all items for a day and their availability
    List<MenuItem> findByAvailableDateAndIsAvailableTrue(LocalDate date);

    // For admin to see all items for a day
    List<MenuItem> findByAvailableDate(LocalDate date);
}
