package com.example.maintenance.service;

import com.example.maintenance.entity.ActionEntity;
import com.example.maintenance.repository.ActionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record ActionService(ActionRepository actionRepository) {
    public void deleteActionTypeById(int id) {
        actionRepository.deleteById(id);
    }

    public List<ActionEntity> getAll() {
        return actionRepository.getAll();
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
