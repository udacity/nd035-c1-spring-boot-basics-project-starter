package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credential")
public class CredentialsController {
    private UserService userService;
    private CredentialService credentialsService;
    private EncryptionService encryptionService;

    public CredentialsController(UserService userService, CredentialService credentialsService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/submit")
    public String submit(Authentication authentication, @ModelAttribute Credential credential){
        User currentUser = userService.getUser(authentication.getName());
        //Generate a random key
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        credential.setUserid(currentUser.getUserid());
        Boolean status=false;
        if (credential.getCredentialid()==null) {
            status=credentialsService.create(credential);
        } else {
            status=credentialsService.update(credential);
        }
        if (status) {
            return "redirect:/result?success&message=Credentials successfully saved!";
        } else {
            return "redirect:/result?error&message=Unable to save credentials! Please try again.";
        }
    }


    @GetMapping("/delete/{credentialsid}")
    public String noteAction(@PathVariable("credentialsid") Integer credentialsid, Authentication authentication) {
        User currentUser = userService.getUser(authentication.getName());
        Boolean status=credentialsService.deleteCredential(currentUser.getUserid(), credentialsid);
        if (status) {
            return "redirect:/result?success&message=Credentials successfully deleted!";
        } else {
            return "redirect:/result?error&message=Unable to delete credentials! Please try again.";
        }
    }
}
