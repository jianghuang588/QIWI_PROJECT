package com.example.mvp.enums;

// Major enum
public enum Major {
    BUSINESS_ENGINEERING("Business Engineering"),
    ECONOMIC_SCIENCES("Economic Sciences"),
    MEDICINE("Medicine");

    private final String displayName;

    Major(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
