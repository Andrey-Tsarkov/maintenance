package com.example.maintenance.service;

import com.example.maintenance.entity.ActionEntity;
import com.example.maintenance.repository.ActionRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ActionService {
    private final ActionRepository actionRepository;

    public ActionService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    public void deleteById(int id) {
        actionRepository.deleteById(id);
    }

    public HashMap<Integer, ActionEntity> getHashMapAll() {
        List<ActionEntity> actionEntities = actionRepository.getAll();

        HashMap<Integer, ActionEntity> actionEntityHashMap = new HashMap<>();
        for (ActionEntity actionEntity: actionEntities) {
            actionEntityHashMap.put(actionEntity.getId(), actionEntity);
        }

        return actionEntityHashMap;
    }

    public List<ActionEntity> getAll() {
        return actionRepository.getAll();
    }

    public List<ActionEntity> getReport() {
        return actionRepository.getReport();
    }

    public ActionEntity getById(int id) {
        return actionRepository.getById(id);
    }

    public void save(ActionEntity actionEntity) {
        actionRepository.save(actionEntity);
    }

    public ActionEntity create(ActionEntity actionEntity) {
        return actionRepository.create(actionEntity);
    }
}
