package com.example.maintenance.repository;

import com.example.maintenance.entity.ActionEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ActionRepository {
    private final EntityManager entityManager;

    public ActionEntity getById(int id) {
        return entityManager.find(ActionEntity.class, id);
    }

    public ActionRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ActionEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM ActionEntity e", ActionEntity.class).getResultList();
    }

    public void deleteById(int id) {
        ActionEntity actionEntity = this.getById(id);
        entityManager.remove(actionEntity);
    }

    public void save(ActionEntity actionEntity) {
        entityManager.merge(actionEntity);
    }

    public ActionEntity create(ActionEntity actionEntity) {
        entityManager.persist(actionEntity);

        return actionEntity;
    }
}
