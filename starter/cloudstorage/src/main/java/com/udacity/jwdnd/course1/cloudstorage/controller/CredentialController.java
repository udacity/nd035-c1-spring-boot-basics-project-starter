package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.data.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.data.Message;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/credential")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @PostMapping("/add")
    public String addCredential(CredentialForm credentialForm, Model model) {


        Message message;
        if (credentialForm.getCredentialId() == null) {

            message = credentialService.addCredential(credentialForm);

        } else {

            message = credentialService.editCredential(credentialForm);

        }
        model.addAttribute("message", message.getMsg());
        model.addAttribute("success", message.isSuccess());
        model.addAttribute("error", message.isError());
        return "result";

    }

    @GetMapping("/delete")
    public String deleteCredential(@RequestParam("id") Integer id, Model model) {

        Message message = credentialService.deleteCredential(id);

        model.addAttribute("message", message.getMsg());
        model.addAttribute("success", message.isSuccess());
        model.addAttribute("error", message.isError());

        return "result";

    }
}
