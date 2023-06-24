package com.vnco.fusiontech.order.entity;


import java.util.Map;

public enum OrderStatusGroup {
    VERIFY("Chờ xác nhận"),
    PROCESSING("Đang xử lý"),
    OUT_FOR_DELIVERY("Đang giao"),
    COMPLETED("Hoàn thành"),
    CANCELLED("Đã huỷ");
    
    private final String detailName;
    
    OrderStatusGroup(String detailName) {
        this.detailName = detailName;
    }
    
    public String detailName() {
        return detailName;
    }
    
    public Map<String, Object> getFullDetail(){
        return Map.of("id", ordinal(), "name", name(), "detailName", detailName);
    }
    
}
