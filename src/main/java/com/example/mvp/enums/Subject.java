package com.example.mvp.enums;

public enum Subject {
    // Business Engineering
    CORPORATE_FINANCE("Corporate Finance", Major.BUSINESS_ENGINEERING),
    FINANCIAL_ACCOUNTING("Financial Accounting", Major.BUSINESS_ENGINEERING),
    BUSINESS_STATISTICS("Business Statistics", Major.BUSINESS_ENGINEERING),

    // Economic Sciences
    MICROECONOMICS("Microeconomics", Major.ECONOMIC_SCIENCES),
    MACROECONOMICS("Macroeconomics", Major.ECONOMIC_SCIENCES),
    ECONOMETRICS("Econometrics", Major.ECONOMIC_SCIENCES),

    // Medicine
    ANATOMY("Anatomy", Major.MEDICINE),
    PHYSIOLOGY("Physiology", Major.MEDICINE),
    BIOCHEMISTRY("Biochemistry", Major.MEDICINE);

    private final String displayName;
    private final Major major;

    Subject(String displayName, Major major) {
        this.displayName = displayName;
        this.major = major;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Major getMajor() {
        return major;
    }
}
