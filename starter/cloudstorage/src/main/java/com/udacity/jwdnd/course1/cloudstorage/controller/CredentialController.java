package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class CredentialController {

    private final UserService userService;
    private final CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/credential")
    public String addCredentialForUser(Authentication authentication, CredentialForm credentialForm, Model model) {
        Integer userId = userService.getUserId(authentication.getName());

        if (credentialService.isCredentialInDatabase(credentialForm.getUrl(), credentialForm.getUserName())) {
            model.addAttribute("errorMessage", "A credential for this url and username already exists!");
        } else {
            try {
                if (credentialForm.getCredentialId() == null) {
                    credentialService.addCredential(userId, credentialForm);
                } else {
                    credentialService.editCredential(credentialForm);
                }
                model.addAttribute("successMessage", true);
            } catch (Exception e) {
                model.addAttribute("errorMessage", e.getLocalizedMessage());
            }
        }

        return "result";
    }

    @GetMapping("/credential/delete")
    public String deleteCredential(@RequestParam("credentialId") Integer credentialId, Model model) {

        try {
            credentialService.deleteCredential(credentialId);
            if (credentialService.getCredentialById(credentialId) == null) {
                model.addAttribute("successMessage", true);
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getLocalizedMessage());
        }

        return "result";
    }
}

