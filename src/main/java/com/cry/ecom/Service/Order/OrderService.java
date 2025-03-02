package com.cry.ecom.Service.Order;

import com.cry.ecom.Dto.OrderDto;
import com.cry.ecom.Entity.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long userId);

    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
