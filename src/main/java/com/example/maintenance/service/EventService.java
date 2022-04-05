package com.example.maintenance.service;

import com.example.maintenance.entity.EventEntity;
import com.example.maintenance.repository.EventJpaRepository;
import com.example.maintenance.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    private final int LIMIT = 10;
    private final EventRepository eventRepository;

    private final EventJpaRepository eventJpaRepository;

    public EventService(EventRepository eventRepository, EventJpaRepository eventJpaRepository) {
        this.eventRepository = eventRepository;
        this.eventJpaRepository = eventJpaRepository;
    }

    public void deleteById(int id) {
        eventRepository.deleteById(id);
    }

    public Page<EventEntity> getCustomList(Pageable pageable) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "mileage"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "eventDate"));
        pageable = PageRequest.of(pageable.getPageNumber(), this.LIMIT, Sort.by(orders));

        return eventJpaRepository.findAll(pageable);
    }

    public EventEntity getById(int id) {
        return eventRepository.getById(id);
    }

    public void save(EventEntity eventEntity) {
        eventRepository.save(eventEntity);
    }

    public EventEntity create(EventEntity eventEntity) {
        return eventRepository.create(eventEntity);
    }
}
