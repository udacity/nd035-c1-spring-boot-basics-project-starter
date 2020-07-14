package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.HomeService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    UserService userService;
    HomeService homeService;
    List<Notes> allNotes;
    List<Credentials> allCredentials;
    List<Files> allFiles;

    public HomeController(UserService userService, HomeService homeService) {
        System.out.println("Built the HomeController");
        this.userService = userService;
        this.homeService = homeService;
    }

    @RequestMapping("/home")
    public String getHomePage(HomeForm homeForm, Model model) {
        System.out.println(">>> getHomePage!");
        allNotes = homeService.getAllNotes(getUserId());
        allCredentials = homeService.getAllCredentials(getUserId());
        allFiles = homeService.getAllFiles(getUserId());
        model.addAttribute("allNotes", allNotes);
        model.addAttribute("allCredentials", allCredentials);
        model.addAttribute("allFiles", allFiles);
        System.out.println("<<< getHomePage");
        return "home";
    }

    @RequestMapping("/addOrUpdateNote")
    public String addOrUpdateNote(HomeForm homeForm, Model model) {
        homeForm.setUserId(getUserId());
        int success;
        if (homeForm.getNoteAction().equals("addNote")) {
            success = homeService.addNote(homeForm);
        } else {
            success = homeService.updateNote(homeForm);
        }
        model.addAttribute("resultSuccess", (success == 1) ? true : false);
        model.addAttribute("tab", "notes");
        model.addAttribute("nextAction", "home");
        return "result";
    }

    @RequestMapping("/deletenote")
    public String deleteNote(@RequestParam String noteid, Model model) {
        System.out.println("We are here in deleteNote");
        int id = Integer.parseInt(noteid);
        int userid = getUserId();
        int success = homeService.deleteNote(id, userid);
        model.addAttribute("resultSuccess", (success == 1) ? true : false);
        return "result";
    }

    @RequestMapping("/addOrUpdateCredentials")
    public String addOrUpdateCredentials(CredentialForm credentialForm, Model model) {
        credentialForm.setUserId(getUserId());
        int success;
        if (credentialForm.getCredentialAction().equals("addCredential")) {
            success = homeService.addCredentials(credentialForm);
        } else {
            success = homeService.updateCredential(credentialForm);
        }
        model.addAttribute("resultSuccess", (success == 1) ? true : false);
        model.addAttribute("tab", "credential");
        model.addAttribute("nextAction", "home");
        return "result";
    }

    @RequestMapping("/deleteCredential")
    public String deleteCredentials(@RequestParam String credentialid, Model model) {
        System.out.println("We are here in deleteCredentials");
        int id = Integer.parseInt(credentialid);
        int userid = getUserId();
        int success = homeService.deleteCredential(id, userid);
        model.addAttribute("resultSuccess", (success == 1) ? true : false);
        return "result";
    }

    @PostMapping("/fileupload")
    public String uploadFile2(@RequestParam("fileUpload") MultipartFile file, Model model) throws IOException {
        int success = 1;
        // check if file is empty
        if (file.isEmpty()) {
            // TODO - supply a reason?
            model.addAttribute("resultSuccess", (success != 1) ? true : false);
            return "result";
        }

        Files files = new Files();
        // normalize the file path
        files.setFilename(StringUtils.cleanPath(file.getOriginalFilename()));
        files.setFilesize("1000");
        files.setContenttype(file.getContentType());
        files.setUserid(getUserId());
        files.setFiledata(file.getBytes());
        success = homeService.addFiles(files);
        model.addAttribute("resultSuccess", (success == 1) ? true : false);

        return "result";
    }

    private int getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = userService.getUser(authentication.getName());
            return user.getUserid();
        }
        // TODO - should we throw an error?? Shouldn't happen since the user should be logged in
        // TODO - but what if the authentication isn't type of AnyonymousAuthenticationToken
        return 0;
    }
}
