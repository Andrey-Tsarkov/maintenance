package com.example.maintenance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainWebController extends AbstractWebController {
    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("content", "base/home");
        model.addAttribute("title", "Главная");

        return this.getBaseLayoutTemplate();
    }

    @GetMapping(value = "report")
    public String viewPicture(Model model) {
        model.addAttribute("content", "report/index");
        model.addAttribute("title", "Отчет");

        return this.getBaseLayoutTemplate();

    }
}
