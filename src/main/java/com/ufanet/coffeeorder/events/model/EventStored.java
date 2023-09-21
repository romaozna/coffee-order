package com.ufanet.coffeeorder.events.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ufanet.coffeeorder.orders.model.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "EVENTS")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EventStored {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventId;

    @Column(name = "ORDER_ID")
    private int orderId;

    @Column(name = "STATUS", nullable = false)
    private OrderStatus status;

    @Column(name = "EVENT", nullable = false)
    private String event;

    @Column(name = "CREATED", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime created;
}
