package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.data.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.data.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.data.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {


    @Autowired
    private FileService fileService;

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private NoteService noteService;

    @GetMapping
    public String home(Model model) {

//        if (fileService.getFiles().isEmpty()) {
//            model.addAttribute("fileList", fileService.getFiles());
//        }
        model.addAttribute("fileList", fileService.getFiles());
        model.addAttribute("credentialList", credentialService.getCredentials());
        model.addAttribute("noteList", noteService.getNotes());
        return "home";
    }

    @ModelAttribute("fileForm")
    public FileForm initFileForm() {
        FileForm fileForm = new FileForm();
        fileForm.setFileUpload(null);
        return fileForm;
    }

    @ModelAttribute("credentialForm")
    public CredentialForm initCredentialForm() {
        CredentialForm credentialForm = new CredentialForm(null, null, null, null, null, null);

        return credentialForm;
    }

    @ModelAttribute("noteForm")
    public NoteForm initNoteForm() {
        NoteForm noteForm = new NoteForm();
        return noteForm;
    }
}
