package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String getUserRegistrationForm(User user, Model mode){
        return "signup";
    }

    @PostMapping("/signup")
    public String registerNewUser(Authentication authentication, User user, Model mode){
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            user = userService.registerNewUser(user);
            System.out.println(user.toString());


            return "signup";
        }
        return "/home";//TODO need to add redirection to home page - is this correct? (will calling home controller gethomepage method work??)
    }

    @GetMapping("/login")
    public String getLoginForm(Authentication authentication){
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }

        return "/home";//TODO need to add redirection
    }
/*
    @PostMapping("/logout")
    public String logout(Model model){
        return "login";
    }*/
}
