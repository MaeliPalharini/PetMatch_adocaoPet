package com.petmatch.backend.model;

public enum Role {
    ANUNCIANTE, ADOTANTE;

    public static Role fromString(String value) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + value);
    }
}

