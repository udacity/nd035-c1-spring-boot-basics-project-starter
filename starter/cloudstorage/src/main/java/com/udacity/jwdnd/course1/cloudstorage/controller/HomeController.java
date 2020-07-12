package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.HomeForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.HomeService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    UserService userService;
    HomeService homeService;
    List<Notes> allNotes;

    public HomeController(UserService userService, HomeService homeService) {
        System.out.println("Built the HomeController");
        this.userService = userService;
        this.homeService = homeService;
    }

    @RequestMapping("/home")
    public String getHomePage(HomeForm homeForm, Model model) {
        System.out.println("We are in getHomePage!");
        allNotes = homeService.getAllNotes();
        model.addAttribute("allNotes", allNotes);
        System.out.println("Size of allNotes: " + allNotes.size());
        return "home";
    }

    @RequestMapping("/addnote")
    public String addOrUpdateNote(HomeForm homeForm, Model model) {
        homeForm.setUserId(getUserId());
        if (homeForm.getNoteAction().equals("addNote")) {
            int noteId = homeService.addNote(homeForm);
        } else {
            System.out.println("Contents of noteId: " + homeForm.getNoteId());
            homeService.updateNote(homeForm);
        }
        // TODO - handle errors?
        model.addAttribute("resultSuccess", true);
        model.addAttribute("tab", "notes");
        model.addAttribute("nextAction", "home");
        return "result";
    }

    @RequestMapping("/deletenote")
    public String deleteNote(@RequestParam String noteid, Model model) {
        System.out.println("We are here in deleteNote");
        int id = Integer.parseInt(noteid);
        homeService.deleteNote(id);
        model.addAttribute("resultSuccess", true);
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
