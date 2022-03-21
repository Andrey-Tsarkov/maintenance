package com.example.maintenance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("title", "Главная");
        return "base/index";
    }
}
