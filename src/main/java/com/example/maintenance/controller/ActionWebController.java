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
@RequestMapping(value = "action")
public class ActionWebController extends AbstractWebController {
    private final ActionService actionService;

    public ActionWebController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping(value = "")
    public String viewList(Model model) {
        model.addAttribute("content", "action/index");
        model.addAttribute("title", "Справочник регламентных работ");
        model.addAttribute("actions", actionService.getAll());

        return this.getBaseLayoutTemplate();
    }

    @GetMapping(value = "/create-form")
    public String viewCreateForm(Model model) {
        model.addAttribute("title", "Создание регламентной работы");
        model.addAttribute("content", "action/create");
        model.addAttribute("actionEntity", new ActionEntity());

        return this.getBaseLayoutTemplate();
    }

    @PostMapping(value = "/add")
    public String post(
            @ModelAttribute("actionEntity") @Valid ActionEntity actionEntity,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Создание регламентной работы (уточнение полей)");
            model.addAttribute("content", "action/create");
            model.addAttribute("actionEntity", actionEntity);

            return this.getBaseLayoutTemplate();
        }

        actionService.create(actionEntity);

        return "redirect:/action";
    }

    @GetMapping(value = "/{id}/edit-form")
    public String viewEditForm(
            @PathVariable int id,
            Model model,
            HttpServletResponse response
    ) {
        model.addAttribute("title", "Правка регламентной работы #" + id);
        ActionEntity actionEntity = actionService.getById(id);
        if (null == actionEntity) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

            return this.getLayoutTemplateWith404Details(model, "Не найдена сущность #" + id);
        }

        model.addAttribute("content", "action/edit");
        model.addAttribute("actionEntity", actionEntity);

        return this.getBaseLayoutTemplate();
    }

    @PutMapping(value = "/{id}")
    public String put(
            @PathVariable int id,
            @ModelAttribute("actionEntity") @Valid ActionEntity actionEntity,
            BindingResult bindingResult,
            Model model,
            HttpServletResponse response
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("content", "action/edit");
            model.addAttribute("title", "Правка регламентной работы #" + id + " (уточнение полей)");
            model.addAttribute("actionEntity", actionEntity);

            return this.getBaseLayoutTemplate();
        }

        if (null == actionService.getById(id)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

            return this.getLayoutTemplateWith404Details(model, "Не найдена сущность #" + id);
        }

        actionService.save(actionEntity);

        return "redirect:/action";
    }
}
