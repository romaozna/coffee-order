package com.ufanet.coffeeorder.events.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.ufanet.coffeeorder.events.dto.*;
import com.ufanet.coffeeorder.events.model.EventStored;
import com.ufanet.coffeeorder.events.model.OrderEvent;
import com.ufanet.coffeeorder.exception.BadRequestException;
import com.ufanet.coffeeorder.orders.model.OrderStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class EventMapper {

    static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static EventStored toEventStored(OrderEvent event) {
        if (event instanceof RegisteredOrder) {
            return EventStored.builder()
                    .orderId(((RegisteredOrder) event).getOrderId())
                    .status(OrderStatus.REGISTERED)
                    .event(gson.toJson(event))
                    .created(LocalDateTime.now())
                    .build();
        }

        if (event instanceof InWorkOrder) {
            return EventStored.builder()
                    .orderId(((InWorkOrder) event).getOrderId())
                    .status(OrderStatus.IN_WORK)
                    .event(gson.toJson(event))
                    .created(LocalDateTime.now())
                    .build();
        }

        if (event instanceof InReadyOrder) {
            return EventStored.builder()
                    .orderId(((InReadyOrder) event).getOrderId())
                    .status(OrderStatus.READY)
                    .event(gson.toJson(event))
                    .created(LocalDateTime.now())
                    .build();
        }

        if (event instanceof IssuedOrder) {
            return EventStored.builder()
                    .orderId(((IssuedOrder) event).getOrderId())
                    .status(OrderStatus.ISSUED)
                    .event(gson.toJson(event))
                    .created(LocalDateTime.now())
                    .build();
        }

        if (event instanceof CancelledOrder) {
            return EventStored.builder()
                    .orderId(((CancelledOrder) event).getOrderId())
                    .status(OrderStatus.CANCELED)
                    .event(gson.toJson(event))
                    .created(LocalDateTime.now())
                    .build();
        }

        throw new BadRequestException("Unsupported event");
    }

    public static OrderEvent toOrderEvent(EventStored event) {
        OrderEvent orderEvent = null;

        switch (event.getStatus()) {
            case REGISTERED -> {
                orderEvent = gson.fromJson(event.getEvent(), RegisteredOrder.class);
            }

            case IN_WORK -> {
                orderEvent = gson.fromJson(event.getEvent(), InWorkOrder.class);
            }

            case READY -> {
                orderEvent = gson.fromJson(event.getEvent(), InReadyOrder.class);
            }

            case ISSUED -> {
                orderEvent = gson.fromJson(event.getEvent(), IssuedOrder.class);
            }

            case CANCELED -> {
                orderEvent = gson.fromJson(event.getEvent(), CancelledOrder.class);
            }
        }
        return orderEvent;
    }

    private static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        private static final DateTimeFormatter formatterWriter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        private static final DateTimeFormatter formatterReader = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        @Override
        public void write(final JsonWriter jsonWriter, final LocalDateTime localDateTime) throws IOException {
            jsonWriter.value(localDateTime.format(formatterWriter));
        }

        @Override
        public LocalDateTime read(final JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString(), formatterReader);
        }
    }
}
