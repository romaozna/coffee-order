package com.ufanet.coffeeorder.orders.service;

import com.ufanet.coffeeorder.events.model.OrderEvent;
import com.ufanet.coffeeorder.orders.model.Order;

public interface OrderService {

    void publishEvent(OrderEvent event);

    Order findOrder(int id);

}