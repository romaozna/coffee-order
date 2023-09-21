package com.ufanet.coffeeorder.orders.controller;

import com.ufanet.coffeeorder.orders.model.Order;
import com.ufanet.coffeeorder.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @GetMapping("{id}")
    public Order getById(@PathVariable Integer id) {
        return orderService.findOrder(id);
    }
}
