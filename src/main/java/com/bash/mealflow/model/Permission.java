package com.bash.mealflow.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {
    ADMIN_CREATE("admin:create");
    private final String permission;

}
