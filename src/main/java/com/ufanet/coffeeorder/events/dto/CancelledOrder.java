package com.ufanet.coffeeorder.events.dto;

import com.ufanet.coffeeorder.events.model.OrderEvent;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class CancelledOrder implements OrderEvent {
    private int orderId;
    private int employeeId;
    private String reason;
    private LocalDateTime createdAt;
}
