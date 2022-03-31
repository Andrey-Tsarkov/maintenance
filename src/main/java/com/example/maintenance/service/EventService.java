package com.example.maintenance.service;

import com.example.maintenance.entity.EventEntity;
import com.example.maintenance.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void deleteById(int id) {
        eventRepository.deleteById(id);
    }

    public List<EventEntity> getAll() {
        return eventRepository.getAll();
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
