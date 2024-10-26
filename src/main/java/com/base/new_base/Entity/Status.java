package com.base.new_base.Entity;

import lombok.Getter;

@Getter

public enum Status {
    ACTIVE("ACTIVE"),
    ADOPTED("ADOPTED"),
    DELIVERED("DELIVERED");
    private final String status;

    Status(String status) {
        this.status = status;
    }
}
