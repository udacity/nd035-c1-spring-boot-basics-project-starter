package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import java.security.SecureRandom;
import java.util.Base64;
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

  private static final String SUCCESS_FIELD = "success";
  private static final String ERROR_FIELD = "error";
  private static final String SIGNUP_TEMPLATE_NAME = "signup";
  private final UserService userService;
  private final HashService hashService;

  @GetMapping
  public String getSignup(User user, Model model) {
    return SIGNUP_TEMPLATE_NAME;
  }

  @PostMapping
  public String postSignup(User user, Model model) {

    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    String encodedSalt = Base64.getEncoder().encodeToString(salt);
    String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

    try {
      userService.createUser(
          User.builder()
              .username(user.getUsername())
              .password(hashedPassword)
              .firstName(user.getFirstName())
              .lastName(user.getFirstName())
              .salt(encodedSalt)
              .build());
      model.addAttribute(SUCCESS_FIELD, true);
    } catch (Exception e) {
      model.addAttribute(ERROR_FIELD, true);
    }

    return SIGNUP_TEMPLATE_NAME;
  }
}
