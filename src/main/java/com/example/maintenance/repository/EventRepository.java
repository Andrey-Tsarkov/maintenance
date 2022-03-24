package com.example.maintenance.repository;

import com.example.maintenance.entity.EventEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class EventRepository {
    private final EntityManager entityManager;

    public EventRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EventEntity getById(int id) {
        return entityManager.find(EventEntity.class, id);
    }

    public List<EventEntity> getAll() {
        List<EventEntity> result =  entityManager.createQuery(
                "SELECT e FROM EventEntity e LEFT JOIN FETCH e.action ORDER BY e.mileage DESC, e.eventDate DESC",
                EventEntity.class
        ).getResultList();

        return result;
    }

    public void deleteById(int id) {
        EventEntity eventEntity = this.getById(id);
        entityManager.remove(eventEntity);
    }

    public void save(EventEntity eventEntity) {
        entityManager.merge(eventEntity);
    }

    public EventEntity create(EventEntity eventEntity) {
        entityManager.persist(eventEntity);

        return eventEntity;
    }
}
