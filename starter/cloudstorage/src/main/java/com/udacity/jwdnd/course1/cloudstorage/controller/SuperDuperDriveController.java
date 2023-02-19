package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SuperDuperDriveController {

    @GetMapping(path = "/home")
    public String getHomePage(Model model) {
        return "home";
    }

    @GetMapping(path = "/signup")
    public String getSignupForm(Model model) {

        return "signup";
    }

    @PostMapping(path = "/signup")
    public String doSignup(Model model) {

        model.addAttribute("isSignupSuccess", true);
        model.addAttribute("signupSuccessMess", "You successfully signed up! " +
                "Please " +
                "continue " +
                "to " +
                "the <a href=\"/login\">login</a> page.");
        return "signup";
    }

    @GetMapping(path = "/login")
    public String login(Model model) {

        return "login";
    }
}
