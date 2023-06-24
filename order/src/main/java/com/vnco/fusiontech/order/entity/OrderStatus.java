package com.vnco.fusiontech.order.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public enum OrderStatus {
    PLACED(OrderStatusGroup.VERIFY, "Chờ xác nhận"),
    VERIFIED(OrderStatusGroup.PROCESSING, "Đã xác nhận"),
    PREPARED(OrderStatusGroup.PROCESSING, "Đang chuẩn bị"),
    ON_DELIVERY(OrderStatusGroup.OUT_FOR_DELIVERY, "Đang giao"),
    DELIVERED_SUCCESS(OrderStatusGroup.COMPLETED, "Giao thành công"),
    DELIVERED_FAILED(OrderStatusGroup.COMPLETED, "Trả hàng"),
    CANCELLED(OrderStatusGroup.CANCELLED, "Đã huỷ"),
    DENIED(OrderStatusGroup.CANCELLED, "Đã bị từ chối");
    
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
        return Set.of(CANCELLED, DELIVERED_SUCCESS, DELIVERED_FAILED, DENIED).contains(this);
    }
    
    public static List<OrderStatus> soldStatusList() {
        return List.of(PLACED, VERIFIED, PREPARED, ON_DELIVERY, DELIVERED_SUCCESS);
    }
    
    public boolean isCompleted() {
        return Set.of(DELIVERED_SUCCESS, DELIVERED_FAILED).contains(this);
    }
    
    public Map<String, Object> getFullStatus() {
        return Map.of("id", ordinal(), "name", name(), "detailName", detailName, "group", group.name()
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
