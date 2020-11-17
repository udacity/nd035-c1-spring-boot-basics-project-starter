package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
	@GetMapping
	public String getSignupPage() {
		return "signup";
	}
	
	@PostMapping
	public String registerUser() {
		return "signup";
	}
}
