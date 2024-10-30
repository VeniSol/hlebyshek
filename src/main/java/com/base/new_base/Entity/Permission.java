package com.base.new_base.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permission {
    ADMIN("ADMIN"),
    DELIVERY("DELIVERY"),
    DISPATCHER("DISPATCHER"),
    USER("USER");
    private final String permission;
}
