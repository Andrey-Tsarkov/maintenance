package com.example.maintenance.controller;

import com.example.maintenance.entity.EventEntity;
import com.example.maintenance.service.EventService;
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

    public EventWebController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "")
    public String viewList(Model model) {
        model.addAttribute("content", "event/index");
        model.addAttribute("title", "Проведенные работы");
        model.addAttribute("events", eventService.getAll());

        return this.getBaseLayoutTemplate();
    }

    @GetMapping(value = "create-form")
    public String viewCreateForm(Model model) {
        model.addAttribute("title", "Создание проведеной работы");
        model.addAttribute("content", "event/create");
        model.addAttribute("eventEntity", new EventEntity());

        return this.getBaseLayoutTemplate();
    }

    @PostMapping(value = "add")
    public String post(@ModelAttribute("eventEntity") @Valid EventEntity eventEntity, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("eventEntity", eventEntity);
            model.addAttribute("title", "Создание проведеной работы (уточнение полей)");
            model.addAttribute("content", "event/create");

            return this.getBaseLayoutTemplate();
        }

        eventService.create(eventEntity);

        return "redirect:/event";
    }

    @GetMapping(value = "{id}/edit-form")
    public String viewEditForm(HttpServletResponse response, Model model, @PathVariable int id) {
        model.addAttribute("title", "Правка проведеной работы #" + id);
        EventEntity eventEntity = eventService.getById(id);
        if (null == eventEntity) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

            return this.get404LayoutTemplate(model, "Не найдена сущность #" + id);
        }

        model.addAttribute("content", "event/edit");
        model.addAttribute("eventEntity", eventEntity);

        return this.getBaseLayoutTemplate();
    }

    @PutMapping(value = "{id}")
    public String put(HttpServletResponse response, @PathVariable int id, @ModelAttribute("eventEntity") @Valid EventEntity eventEntity, BindingResult bindingResult, Model model) {
        model.addAttribute("content", "event/edit");
        model.addAttribute("title", "Правка проведеной работы #" + id + " (уточнение полей)");
        model.addAttribute("eventEntity", eventEntity);

        if (bindingResult.hasErrors()) {
            return this.getBaseLayoutTemplate();
        }

        EventEntity originEventEntity = eventService.getById(id);
        if (null == originEventEntity) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

            return this.get404LayoutTemplate(model, "Не найдена сущность #" + id);
        }

        eventService.save(eventEntity);

        return "redirect:/event";
    }
}
