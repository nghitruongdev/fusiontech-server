package com.vnco.fusiontech.common.constant;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public enum OrderStatus {
    PLACED(OrderStatusGroup.VERIFY, "Cho xác nhận"),
    VERIFIED(OrderStatusGroup.PROCESSING, "Da xác nhận"),
    PREPARED(OrderStatusGroup.PROCESSING, "Dang chuẩn bị"),
    ON_DELIVERY(OrderStatusGroup.ON_DELIVERY, "Dang giao"),
    COMPLETED(OrderStatusGroup.COMPLETED, "Giao thành công"),
    FAILED(OrderStatusGroup.FAILED, "Tra hàng"),
    CANCELLED(OrderStatusGroup.CANCELLED, "Da huỷ"),
    DENIED(OrderStatusGroup.CANCELLED, "Da huỷ bởi hệ thống");
    
    private final OrderStatusGroup group;
    private final String           detailName;
    
    OrderStatus(OrderStatusGroup group, String normalName) {
        this.group = group;
        this.detailName = normalName;
    }
    
    public boolean isCancellable() {
        return Set.of(PLACED, VERIFIED, PREPARED).contains(this);
    }
    
    public boolean isUnchangeable() {
        return Set.of(CANCELLED, COMPLETED, FAILED, DENIED).contains(this);
    }
    
    public static List<OrderStatus> soldStatusList() {
        return List.of(PLACED, VERIFIED, PREPARED, ON_DELIVERY, COMPLETED);
    }
    
    public boolean isCompleted() {
        return Set.of(COMPLETED, FAILED).contains(this);
    }
    
    public Map<String, Object> getFullStatus() {
        return Map.of("id", ordinal(),
                      "name", name(),
                      "detailName", detailName,
                      "group", group.name(),
                      "isChangeable", !isUnchangeable()
        );
    }
    
    public static List<OrderStatus> getStatusesByGroup(OrderStatusGroup group) {
        return Arrays.stream(values()).filter(item -> item.group == group).toList();
    }
    
    public OrderStatusGroup getGroup() {
        return group;
    }
    
    public String getDetailName() {
        return detailName;
    }
    
}
