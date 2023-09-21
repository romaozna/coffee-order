package com.ufanet.coffeeorder.events.dto;

import com.ufanet.coffeeorder.events.model.OrderEvent;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class RegisteredOrder implements OrderEvent {
    private int orderId;
    private int clientId;
    private int employeeId;
    private String expectedTime;
    private int itemId;
    private int itemPrice;
    private LocalDateTime createdAt;
}
