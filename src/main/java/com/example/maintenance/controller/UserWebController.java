package com.example.maintenance.controller;

import com.example.maintenance.entity.UserEntity;
import com.example.maintenance.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "")
public class UserWebController extends AbstractWebController {
    private final UserService userService;

    public UserWebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "login")
    public String getLogin(Model model) {
        model.addAttribute("content", "user/login");
        model.addAttribute("title", "Авторизация");
        model.addAttribute("userEntity", new UserEntity());

        return this.getBaseLayoutTemplate();
    }

    @GetMapping(value = "registration")
    public String getRegistration(Model model) {
        model.addAttribute("content", "user/registration");
        model.addAttribute("title", "Регистрация");
        model.addAttribute("userEntity", new UserEntity());

        return this.getBaseLayoutTemplate();
    }

    @PostMapping(value = "registration")
    public String postRegistration(
            @ModelAttribute("userEntity") @Valid UserEntity userEntity,
            BindingResult bindingResult,
            Model model
    ) {
        model.addAttribute("content", "user/registration");
        model.addAttribute("title", "Регистрация (уточнение данных)");
        model.addAttribute("userEntity", userEntity);

        if (bindingResult.hasErrors()) {
            return this.getBaseLayoutTemplate();
        }

        if (!userEntity.getPassword().equals(userEntity.getPasswordConfirm())) {
            bindingResult.addError(
                    new FieldError(
                            "userEntity",
                            "passwordConfirm",
                            userEntity.getUsername(),
                            false,
                            null,
                            null,
                            "Пароли не совпадают"
                    )
            );

            return this.getBaseLayoutTemplate();
        }
        if (!userService.saveUser(userEntity)) {
            bindingResult.addError(
                    new FieldError(
                            "userEntity",
                            "username",
                            userEntity.getUsername(),
                            false,
                            null,
                            null,
                            "Пользователь с таким именем уже существует"
                    )
            );

            return this.getBaseLayoutTemplate();
        }

        return "redirect:/";
    }

    @GetMapping(value = "/users")
    public String viewUsers(Model model) {
        model.addAttribute("content", "user/index");
        model.addAttribute("title", "Пользователи");
        model.addAttribute("users", userService.getAll());

        return this.getBaseLayoutTemplate();
    }
}
