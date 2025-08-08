package com.example.mvp.enums;

public enum UserRole {
    TUTOR("Tutor"),
    TUTEE("Tutee");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
