package com.cosmetic_manager.cosmetic_manager.utils;

public enum CategoryName {
    SKINCARE,
    MAKEUP,
    HAIRCARE,
    FRAGRANCE,
    BODYCARE,
    NAILCARE;

    public static boolean contains(String name) {
        for (CategoryName category : CategoryName.values()) {
            if (category.name().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
