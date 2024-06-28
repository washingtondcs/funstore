package com.challenge.funstore.domain.enums;

public class OrderTypeEnums {

    public enum OrderType {
        SALE(1, "SALE"),
        RENT(2, "RENT");

        private final int code;
        private final String description;

        OrderType(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        public Integer getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static OrderType findByCode(Integer code) {
            for (OrderType orderType : OrderType.values()) {
                if (orderType.getCode().equals(code)) {
                    return orderType;
                }
            }
            throw new IllegalArgumentException("Invalid code for OrderType: " + code);
        }
    }
}