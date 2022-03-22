package com.example.maintenance.controller;

import com.example.maintenance.service.ActionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/action", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActionRestController {
    private final ActionService actionTypeService;

    public ActionRestController(ActionService actionTypeService) {
        this.actionTypeService = actionTypeService;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable("id") int id) {
        actionTypeService.deleteActionTypeById(id);
    }
}
