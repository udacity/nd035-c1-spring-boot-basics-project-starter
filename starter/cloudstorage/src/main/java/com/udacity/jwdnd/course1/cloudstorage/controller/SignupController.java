package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupView() {
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
        System.out.println("signupUser");
        String signupError = null;

        if (!userService.isUserAvailable(user.getUsername())) {
            signupError = "The username already exists.";
            showError(model, signupError);
        }

        if (signupError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signupError = "There was an error signing up. Please try again";
                showError(model, signupError);
            }

            if (signupError == null) {
                redirectAttributes.addFlashAttribute("signupSuccessMessage", true);
                return "redirect:/login";
            } else {
                System.out.println("Signup error, trying to redirect!");
                showError(model, signupError);
            }
        }

        return "signup";
    }

    private void showError(Model model, String signupError) {
        model.addAttribute("signupError", signupError);
    }
}
