package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    private final FileService fileService;
    private final UserService userService;

    @Autowired
    public HomeController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        User user = userService.getUserByUsername(username);
        Long userId = user.getId();

        List<File> files = null;
        try {
            files = fileService.getAllFiles(userId);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Could not parse file.");
        }

        model.addAttribute("files", files);

        return "home";
    }
}
