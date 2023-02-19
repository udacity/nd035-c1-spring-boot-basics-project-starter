package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SuperDuperDriveController {

    @GetMapping(path = "/home")
    public String getHomePage(Model model) {
        return "home";
    }

    @GetMapping(path = "/login")
    public String login(Model model) {

        return "login";
    }

    @GetMapping(path = "/logout")
    public String logout(Model model) {

        model.addAttribute("logoutSuccessMess", "You have been logged out");
        model.addAttribute("isLogOut", true);
        return "login";
    }
}
