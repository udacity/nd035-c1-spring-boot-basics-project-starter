package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.HomeService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    UserService userService;
    HomeService homeService;
    List<Notes> allNotes;
    List<Credentials> allCredentials;

    public HomeController(UserService userService, HomeService homeService) {
        System.out.println("Built the HomeController");
        this.userService = userService;
        this.homeService = homeService;
    }

    @RequestMapping("/home")
    public String getHomePage(HomeForm homeForm, Model model) {
        System.out.println("We are in getHomePage!");
        allNotes = homeService.getAllNotes(getUserId());
        allCredentials = homeService.getAllCredentials(getUserId());
        model.addAttribute("allNotes", allNotes);
        model.addAttribute("allCredentials", allCredentials);
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

    @RequestMapping("/fileupload")
    public String fileUpload(FileForm fileForm, Model model) throws IOException {
        System.out.println("We are going to upload file...");

        if (fileForm != null) {
            System.out.println("The form is not NULL!");
        }

//        ModelAndView modelAndView = new ModelAndView("fileUpload");
//        InputStream in = file.getInputStream();
//        File currDir = new File(".");
//        String path = currDir.getAbsolutePath();
//        FileOutputStream f = new FileOutputStream(
//                path.substring(0, path.length()-1)+ file.getOriginalFilename());
//        int ch = 0;
//        while ((ch = in.read()) != -1) {
//            f.write(ch);
//        }
//
//        f.flush();
//        f.close();
//
//        modelAndView.getModel().put("message", "File uploaded successfully!");

        int success = 1;
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
