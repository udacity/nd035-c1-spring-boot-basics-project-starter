package safwat.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	
	@GetMapping
	public String getLoginPage() {
		return "login";
	}
	
	
	@GetMapping("/{success}")
	public String getLoginPageRedirected(@PathVariable(value = "success") boolean success, Model model) {
		
		model.addAttribute("success", true);
		return "login";
	}
}
