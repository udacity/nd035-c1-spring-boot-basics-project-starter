package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.constants.Templates;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@ControllerAdvice
@RequiredArgsConstructor
public class ResultController {

  @GetMapping("/results")
  public String getHome(Principal principal, Model model) {
    val username = principal.getName();

    return Templates.RESULT;
  }
}
