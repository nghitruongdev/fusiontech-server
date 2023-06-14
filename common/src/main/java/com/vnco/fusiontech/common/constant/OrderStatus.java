package com.vnco.fusiontech.common.constant;

import java.util.Set;

public enum OrderStatus {
    WAIT_FOR_ACCEPT("Đang chờ xác nhận"),
    ACCEPTED("Đã xác nhận"),
    PAYMENT_ACCEPTED("Đã thanh toán"),
    ON_DELIVERY("Đang trên đường giao"),
    DELIVERED_SUCCESS("Đã giao hàng thành công"),
    DELIVERED_FAILED("Giao hàng thất bại"),
    CANCELLED("Huỷ đơn hàng");
    
    private String readableName;
    
    private OrderStatus(String readableName) {
        this.readableName = readableName;
    }
    
    public String readableName() {
        return readableName;
    }
    
    private static Set<OrderStatus> finalStatus() {
        return Set.of(CANCELLED, DELIVERED_SUCCESS, DELIVERED_FAILED);
    }
    
    public static boolean isFinal(OrderStatus status) {
        return finalStatus().contains(status);
    }
    
    public static boolean isOnDeliver(OrderStatus status) {
        return ON_DELIVERY == status;
    }
    
}
