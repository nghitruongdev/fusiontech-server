package com.vnco.fusiontech.order.web.rest.request;

import com.vnco.fusiontech.common.constant.OrderStatus;
import jakarta.validation.Valid;

public record UpdateStatusRequest (@Valid OrderStatus status){
}
