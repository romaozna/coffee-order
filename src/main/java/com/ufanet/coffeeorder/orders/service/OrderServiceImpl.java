package com.ufanet.coffeeorder.orders.service;

import com.ufanet.coffeeorder.events.mapper.EventMapper;
import com.ufanet.coffeeorder.events.model.EventStored;
import com.ufanet.coffeeorder.events.model.OrderEvent;
import com.ufanet.coffeeorder.events.repository.EventRepository;
import com.ufanet.coffeeorder.exception.BadRequestException;
import com.ufanet.coffeeorder.exception.NotFoundException;
import com.ufanet.coffeeorder.orders.model.Order;
import com.ufanet.coffeeorder.orders.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
   private final EventRepository eventRepository;

    @Override
    public void publishEvent(OrderEvent event) {
        EventStored eventStored = EventMapper.toEventStored(event);
        int orderId = eventStored.getOrderId();

        if (eventStored.getStatus() != OrderStatus.REGISTERED && getFirstEvent(orderId).isEmpty()) {
            throw new BadRequestException("Order must be register before " + eventStored.getStatus());
        }

        Optional<EventStored> lastEvent = getLastEvent(orderId);
        if (lastEvent.isPresent() && (lastEvent.get().getStatus() == OrderStatus.ISSUED || lastEvent.get().getStatus() == OrderStatus.CANCELED)) {
            throw new BadRequestException("The order was " + lastEvent.get().getStatus());
        }


        eventRepository.save(eventStored);
    }

    @Override
    public Order findOrder(int id) {
        EventStored eventStored = getLastEvent(id)
                .orElseThrow(() -> new NotFoundException("Order with id = " + id + " not found"));

        List<OrderEvent> eventStream = eventRepository.findAllByOrderIdOrderByCreatedDesc(id)
                .stream()
                .map(EventMapper::toOrderEvent).toList();

        return Order.builder()
                        .id(eventStored.getOrderId())
                        .status(eventStored.getStatus())
                        .events(eventStream)
                        .build();
    }

    private Optional<EventStored> getLastEvent(int id) {
        return eventRepository.findAllByOrderIdOrderByCreatedDesc(id).stream().findFirst();
    }

    private Optional<EventStored> getFirstEvent(int id) {
        return eventRepository.findAllByOrderIdOrderByCreatedAsc(id).stream().findFirst();
    }
}
