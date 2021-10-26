package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/result")
public class ResultController {

    private final UserService userService;
    private final NoteService noteService;
    private final FileService fileService;
    private final CredentialService credentialService;

    public ResultController(UserService userService, NoteService noteService, FileService fileService, CredentialService credentialService) {
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String resultView() {
        return "result";
    }

    @PostMapping("/note")
    public String addNoteForUser(Authentication authentication, NoteForm noteForm, Model model) {
        Integer userId = userService.getUserId(authentication.getName());

        try {
            if (noteForm.getNoteId() == null) {
                noteService.addNote(userId, noteForm);
            } else {
                noteService.editNote(userId, noteForm);
            }
            model.addAttribute("successMessage", true);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getLocalizedMessage());
        }

        return "result";
    }

    @GetMapping("/note/delete")
    public String deleteNote(@RequestParam("noteId") Integer noteId, Model model) {

        try {
            noteService.deleteNote(noteId);
            if (noteService.getNoteById(noteId) == null) {
                model.addAttribute("successMessage", true);
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getLocalizedMessage());
        }

        return "result";
    }

    @PostMapping("/file")
    public String uploadFileForUser(Authentication authentication, MultipartFile fileUpload, Model model) {
        Integer userId = userService.getUserId(authentication.getName());

        try {
            if (fileUpload.getSize() > 0) {
                fileService.uploadFile(userId, fileUpload);
                model.addAttribute("successMessage", true);
            } else {
                model.addAttribute("errorMessage", "Did you forget to select a file to upload?");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getLocalizedMessage());
        }

        return "result";
    }

    @GetMapping("/file/view/{fileId}")
    public ResponseEntity<byte[]> viewFile(@PathVariable("fileId") Integer fileId, Model model) {
        System.out.println("Checking file in database with id: " + fileId);
        File file = fileService.getFileById(fileId);
        System.out.println("Found file to display: " + file.getFileId());

        String filename = file.getFileName();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(file.getFileData());
    }

    @GetMapping("/file/delete")
    public String deleteFile(@RequestParam("fileId") Integer fileId, Model model) {

        try {
            fileService.deleteFile(fileId);
            if (fileService.getFileById(fileId) == null) {
                model.addAttribute("successMessage", true);
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getLocalizedMessage());
        }

        return "result";
    }

    @PostMapping("/credential")
    public String addCredentialForUser(Authentication authentication, CredentialForm credentialForm, Model model) {
        Integer userId = userService.getUserId(authentication.getName());

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
