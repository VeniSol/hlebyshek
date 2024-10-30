package com.base.new_base.Entity;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum Role {
    ADMIN(Set.of(Permission.ADMIN,Permission.DELIVERY,Permission.DISPATCHER,Permission.USER)),
    DELIVERY(Set.of(Permission.DELIVERY)),
    DISPATCHER(Set.of(Permission.DISPATCHER)),
    USER(Set.of(Permission.USER));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (Permission permission : getPermissions()) {
            authorities.add(new SimpleGrantedAuthority(permission.getPermission()));
        }
        return authorities;
    }
}
