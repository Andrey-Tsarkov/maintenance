package com.example.maintenance.controller;

import org.springframework.ui.Model;

public abstract class AbstractWebController {
    protected String getBaseLayoutTemplate() {
        return "base/layout";
    }

    protected String getLayoutTemplateWith404Details(Model model, String details) {
        model.addAttribute("content", "base/error");
        model.addAttribute("details", details);

        return this.getBaseLayoutTemplate();
    }
}
