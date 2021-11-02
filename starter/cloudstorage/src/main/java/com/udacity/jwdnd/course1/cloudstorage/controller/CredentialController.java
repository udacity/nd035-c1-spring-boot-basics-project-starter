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

        try {
            if (credentialForm.getCredentialId() == null) {
                if (!credentialService.addCredential(userId, credentialForm)) {
                    model.addAttribute("errorMessage", "There is already a credential for this url and user.");
                } else {
                    model.addAttribute("successMessage", true);
                }
            } else {
                if (!credentialService.editCredential(credentialForm)) {
                    model.addAttribute("errorMessage", "Credential entry has not changed.");
                } else {
                    model.addAttribute("successMessage", true);
                }
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getLocalizedMessage());
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

