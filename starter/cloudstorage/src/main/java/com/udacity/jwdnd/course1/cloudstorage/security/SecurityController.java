package com.udacity.jwdnd.course1.cloudstorage.security;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/signup")
    public String getSignupForm(Model model, @ModelAttribute("user") User user) {

        return "signup";
    }

    @PostMapping(path = "/signup")
    public String doSignup(Model model, @ModelAttribute("user") User user) {

        boolean isCreateUserSuccess = userService.createUser(user) != 0;
        model.addAttribute("isSignupSuccess", isCreateUserSuccess);
        if (isCreateUserSuccess) {
            model.addAttribute("signupMess", "You successfully signed up! " +
                    "Please " +
                    "continue " +
                    "to " +
                    "the <a href=\"/login\">login</a> page.");

        } else {
            model.addAttribute("signupMess", "<span>Example Signup Error Message</span>");
        }
        return "signup";
    }

    @GetMapping(path = "/login")
    public String login(Model model) {

        return "login";
    }
}
