package com.ufanet.coffeeorder.events.repository;

import com.ufanet.coffeeorder.events.model.EventStored;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<EventStored, Integer> {
    List<EventStored> findAllByOrderIdOrderByCreatedDesc(int orderId);

    List<EventStored> findAllByOrderIdOrderByCreatedAsc(int orderId);

}
