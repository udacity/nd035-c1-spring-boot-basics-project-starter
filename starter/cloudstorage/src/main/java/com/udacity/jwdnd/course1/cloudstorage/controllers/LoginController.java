package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

  private final UserService userService;

  @GetMapping
  public String getLogin(User user, Model model) {
    return "login";
  }

  @PostMapping
  public String postLogin(User user, Model model) {
    if (userService.validateCredentials(user)) {
      return "home";
    } else {
      model.addAttribute("error", true);
      return "login";
    }
  }
}
