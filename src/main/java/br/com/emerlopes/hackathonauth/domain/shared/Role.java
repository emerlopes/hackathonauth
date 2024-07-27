package br.com.emerlopes.hackathonauth.domain.shared;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    GUEST("ROLE_GUEST");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public static Role fromRole(String role) {
        for (Role userRole : values()) {
            if (userRole.role.equals(role)) {
                return userRole;
            }
        }
        return null;
    }

}

