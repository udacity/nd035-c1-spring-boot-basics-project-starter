package safwat.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import safwat.cloudstorage.model.User;
import safwat.cloudstorage.services.UserService;

@Controller
@RequestMapping("/signup")
public class SignupController {
	
	UserService userService;
	
	public SignupController(UserService userService) {
		// TODO Auto-generated constructor stub
		this.userService = userService;
	}
	
	
	@GetMapping
	public String getSignupPage() {
		return "signup";
	}
	
	
	@PostMapping
	public void postSignupPage(User user, Model model) {
		//matching occurs by name attribute to populate user
		System.out.println(user.getUserName());
		String error = null;
		if(userService.isUserAvailable(user)) {
			userService.createUser(user);
			model.addAttribute("success", true);
		}
		else {
			error = "userName alredy exists";
			model.addAttribute("error", error);
		}
	}
	
	
}
