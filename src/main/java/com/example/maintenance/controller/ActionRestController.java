package com.example.maintenance.controller;

import com.example.maintenance.entity.ActionEntity;
import com.example.maintenance.service.ActionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/action", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActionRestController {
    private final ActionService actionService;

    public ActionRestController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping(value = "report")
    public List<ActionEntity> getReport() {
        return actionService.getReport();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") int id) {
        actionService.deleteById(id);
    }
}
