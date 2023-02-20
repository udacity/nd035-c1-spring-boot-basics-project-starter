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
}
