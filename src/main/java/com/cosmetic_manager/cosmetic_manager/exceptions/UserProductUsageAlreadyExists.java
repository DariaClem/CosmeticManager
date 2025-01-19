package com.cosmetic_manager.cosmetic_manager.exceptions;

public class UserProductUsageAlreadyExists extends RuntimeException {
    public UserProductUsageAlreadyExists(String message) {
        super(message);
    }
}
