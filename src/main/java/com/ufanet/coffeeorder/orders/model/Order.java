package com.ufanet.coffeeorder.orders.model;

import com.ufanet.coffeeorder.events.model.OrderEvent;
import lombok.*;

import java.util.List;

@Data
@Builder
public class Order {
    Integer id;

    OrderStatus status;

    List<OrderEvent> events;
}
