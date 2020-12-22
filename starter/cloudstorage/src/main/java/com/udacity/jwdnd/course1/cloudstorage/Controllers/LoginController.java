package com.udacity.jwdnd.course1.cloudstorage.Controllers;

import com.udacity.jwdnd.course1.cloudstorage.Model.LoginForm;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String loginView(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login";
    }
}
