package com.ufanet.coffeeorder.events.controller;

import com.ufanet.coffeeorder.events.dto.*;
import com.ufanet.coffeeorder.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Validated
public class EventController {

    private final OrderService orderService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody RegisteredOrder event) {
        orderService.publishEvent(event);
    }

    @PostMapping("/cancel")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CancelledOrder event) {
        orderService.publishEvent(event);
    }

    @PostMapping("/ready")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody InReadyOrder event) {
        orderService.publishEvent(event);
    }

    @PostMapping("/in-work")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody InWorkOrder event) {
        orderService.publishEvent(event);
    }

    @PostMapping("/issue")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody IssuedOrder event) {
        orderService.publishEvent(event);
    }
}
