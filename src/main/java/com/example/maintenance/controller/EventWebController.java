package com.example.maintenance.controller;

import com.example.maintenance.entity.EventEntity;
import com.example.maintenance.service.ActionService;
import com.example.maintenance.service.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "event")
public class EventWebController extends AbstractWebController {
    private final EventService eventService;
    private final ActionService actionService;

    public EventWebController(EventService eventService, ActionService actionService) {
        this.eventService = eventService;
        this.actionService = actionService;
    }

    @GetMapping(value = "")
    public String viewList(Model model, Pageable pageable) {
        model.addAttribute("content", "event/index");
        model.addAttribute("title", "Проведенные работы");

        Page<EventEntity> page = eventService.getCustomList(pageable);
        model.addAttribute("events", page.getContent());
        model.addAttribute("currentPage", page.getNumber());
        model.addAttribute("totalPages", page.getTotalPages());

        model.addAttribute("actionHashMap", actionService.getHashMapAll());

        return this.getBaseLayoutTemplate();
    }

    @GetMapping(value = "create-form")
    public String viewCreateForm(Model model) {
        model.addAttribute("title", "Создание проведеной работы");
        model.addAttribute("content", "event/edit");
        model.addAttribute("eventEntity", new EventEntity());
        model.addAttribute("actions", actionService.getAll());

        return this.getBaseLayoutTemplate();
    }

    @PostMapping(value = "add")
    public String post(
            @ModelAttribute("eventEntity") @Valid EventEntity eventEntity,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Создание проведеной работы (уточнение полей)");
            model.addAttribute("content", "event/edit");
            model.addAttribute("eventEntity", eventEntity);
            model.addAttribute("actions", actionService.getAll());

            return this.getBaseLayoutTemplate();
        }

        eventService.create(eventEntity);

        return "redirect:/event";
    }

    @GetMapping(value = "{id}/edit-form")
    public String viewEditForm(
            @PathVariable int id,
            Model model,
            HttpServletResponse response
    ) {
        model.addAttribute("title", "Правка проведеной работы #" + id);
        EventEntity eventEntity = eventService.getById(id);
        if (null == eventEntity) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

            return this.getLayoutTemplateWith404Details(model, "Не найдена сущность #" + id);
        }

        model.addAttribute("content", "event/edit");
        model.addAttribute("eventEntity", eventEntity);
        model.addAttribute("actions", actionService.getAll());

        return this.getBaseLayoutTemplate();
    }

    @PutMapping(value = "{id}")
    public String put(
            @PathVariable int id,
            @ModelAttribute("eventEntity") @Valid EventEntity eventEntity,
            BindingResult bindingResult,
            Model model,
            HttpServletResponse response
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("content", "event/edit");
            model.addAttribute("title", "Правка проведеной работы #" + id + " (уточнение полей)");
            model.addAttribute("eventEntity", eventEntity);
            model.addAttribute("actions", actionService.getAll());

            return this.getBaseLayoutTemplate();
        }

        if (null == eventService.getById(id)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

            return this.getLayoutTemplateWith404Details(model, "Не найдена сущность #" + id);
        }

        eventService.save(eventEntity);

        return "redirect:/event";
    }
}
