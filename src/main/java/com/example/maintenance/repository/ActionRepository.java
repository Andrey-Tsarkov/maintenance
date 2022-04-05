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

    public ActionRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ActionEntity getById(int id) {
        return entityManager.find(ActionEntity.class, id);
    }

    public List<ActionEntity> getAll() {
        List<ActionEntity> result = entityManager.createQuery(
                "SELECT e FROM ActionEntity e ORDER BY e.sort asc, e.name ASC",
                ActionEntity.class
        ).getResultList();

        return result;
    }

    public List<ActionEntity> getReport() {
        List<ActionEntity> result = entityManager.createQuery(
                "SELECT DISTINCT e FROM ActionEntity e LEFT JOIN FETCH e.events ORDER BY e.sort ASC",
                ActionEntity.class
        ).getResultList();

        return result;
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
