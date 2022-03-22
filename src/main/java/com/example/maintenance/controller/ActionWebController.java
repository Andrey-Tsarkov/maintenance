package com.example.maintenance.controller;

import com.example.maintenance.entity.ActionEntity;
import com.example.maintenance.service.ActionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class ActionWebController extends AbstractWebController {
    private final ActionService actionService;

    public ActionWebController(ActionService actionTypeService) {
        this.actionService = actionTypeService;
    }

    @GetMapping(value = "action")
    public String viewActionList(Model model) {
        model.addAttribute("content", "action/index");
        model.addAttribute("title", "Справочник регламентных работ");
        model.addAttribute("actions", actionService.getAll());

        return this.getBaseLayoutTemplate();
    }

    @GetMapping(value = "action/create-form")
    public String viewCreateActionForm(Model model) {
        model.addAttribute("title", "Создание регламентной работы");
        model.addAttribute("content", "action/create");
        model.addAttribute("actionEntity", new ActionEntity());

        return this.getBaseLayoutTemplate();
    }

    @PostMapping(value = "action/add")
    public String postAddAction(@ModelAttribute("actionEntity") @Valid ActionEntity actionEntity, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("actionEntity", actionEntity);
            model.addAttribute("title", "Создание регламентной работы (уточнение полей)");
            model.addAttribute("content", "action/create-form");
            return this.getBaseLayoutTemplate();
        }

        actionService.create(actionEntity);

        return "redirect:/action";
    }

    @GetMapping(value = "action/{id}/edit-form")
    public String viewEditActionForm(HttpServletResponse response, Model model, @PathVariable int id) {
        model.addAttribute("title", "Правка регламентной работы #" + id);
        ActionEntity actionEntity = actionService.getById(id);
        if (null == actionEntity) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return this.get404LayoutTemplate(model, "Не найдена сущность #" + id);
        }

        model.addAttribute("content", "action/edit");
        model.addAttribute("actionEntity", actionEntity);

        return this.getBaseLayoutTemplate();
    }

    @PutMapping(value = "action/{id}")
    public String putUser(HttpServletResponse response, @PathVariable int id, @ModelAttribute("actionEntity") @Valid ActionEntity actionEntity, BindingResult bindingResult, Model model) {
        model.addAttribute("content", "action/edit");
        model.addAttribute("title", "Правка регламентной работы #" + id + " (уточнение полей)");
        model.addAttribute("actionEntity", actionEntity);

        if (bindingResult.hasErrors()) {
            return this.getBaseLayoutTemplate();
        }

        ActionEntity originActionEntity = actionService.getById(id);
        if (null == originActionEntity) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return this.get404LayoutTemplate(model, "Не найдена сущность #" + id);
        }

        actionService.save(actionEntity);

        return "redirect:/action";
    }
}