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
    public String getUserRegistrationForm(Authentication authentication, User user, Model mode){
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
            return "signup";
        else return "redirect:/home";//TODO need to add redirection to home page - this redirects to page but does not change the url back to home (will calling home controller gethomepage method work??)
    }

    @PostMapping("/signup")
    public String registerNewUser(User user, Model model) {//TODO use a UserForm instead of the actual Entity class

        if(userService.isUsernameAvailable(user.getUsername())) {
            user = userService.registerNewUser(user);
            System.out.println(user.toString());
        }
        else {
            model.addAttribute("usernameUnavailableError", "Username unavailable");//TODO is this the best way to handle this on the front end?
            user.setUsername("");
        }
        model.addAttribute("user", user);

        return "signup";
    }

    @GetMapping("/login")
    public String getLoginForm(Authentication authentication){
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:/home";
    }

}
