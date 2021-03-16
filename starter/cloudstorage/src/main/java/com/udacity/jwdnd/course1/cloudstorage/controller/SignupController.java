package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.constants.Attributes;
import com.udacity.jwdnd.course1.cloudstorage.constants.Templates;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController {

  private final UserService userService;

  @GetMapping
  public String getSignup(User user, Model model) {
    return Templates.SIGNUP;
  }

  @PostMapping
  public String postSignup(User user, Model model) {

    try {
      userService.createUser(user);
      model.addAttribute(Attributes.SUCCESS, true);
    } catch (Exception e) {
      model.addAttribute(Attributes.ERROR, true);
    }

    return Templates.SIGNUP;
  }
}
