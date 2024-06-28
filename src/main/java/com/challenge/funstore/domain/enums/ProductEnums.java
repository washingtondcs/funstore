package com.challenge.funstore.domain.enums;

public class ProductEnums {

    public enum AvailableFor {
        SALE(1, "SALE"),
        RENT(2, "RENT"),
        RENT_AND_SALE(3, "RENT_AND_SALE");

        private final int code;
        private final String description;

        AvailableFor(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        public Integer getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static AvailableFor findByCode(Integer code) {
            for (AvailableFor availableFor : AvailableFor.values()) {
                if (availableFor.getCode().equals(code)) {
                    return availableFor;
                }
            }
            throw new IllegalArgumentException("Invalid code for AvailableFor: " + code);
        }
    }

    public enum ProductType {
        BOOK(1, "BOOK"),
        MOVIE(2, "MOVIE"),
        SERIES(3, "SERIES");

        private final int code;
        private final String displayName;

        ProductType(Integer code, String displayName) {
            this.code = code;
            this.displayName = displayName;
        }

        public Integer getCode() {
            return code;
        }

        public String getDisplayName() {
            return displayName;
        }

        public static ProductType findByCode(Integer code) {
            for (ProductType type : ProductType.values()) {
                if (type.getCode().equals(code)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid code for ProductType: " + code);
        }
    }
}