package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileEntity;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/")
public class HomeController {

    private final UserService userService;
    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;

    private final String MODEL_ATTRIBUTE_ERROR_MESSAGE = "errorMessage";

    HomeController(UserService userService,
                   FileService fileService,
                   NoteService noteService,
                   CredentialService credentialService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String getHome(Model model, Authentication authentication) {
        int userId = this.userService.getUserId(authentication.getName());
        model.addAttribute("fileList", this.fileService.getFiles(userId));
        model.addAttribute("noteList", this.noteService.getNotes(userId));
        model.addAttribute("credentialList", this.credentialService.getCredentials(userId));
        return "home";
    }

    // download file. select by userid and fileid
    @GetMapping(value = "file/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getFile(@PathVariable("fileId") int fileId, Authentication authentication) {
        int userId = this.userService.getUserId(authentication.getName());
        FileEntity fileEntity = this.fileService.getFile(userId, fileId);
        if (fileEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fileEntity.getFileData());
    }

    // upload file. check if filename already exists for userid
    @PostMapping("file")
    public String postFile(@RequestParam("fileUpload") MultipartFile file, Model model, Authentication authentication) {
        int userId = this.userService.getUserId(authentication.getName());
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            model.addAttribute(MODEL_ATTRIBUTE_ERROR_MESSAGE, "Please select a file for upload!");
        } else if (this.fileService.filenameExists(userId, fileName)) {
            model.addAttribute(MODEL_ATTRIBUTE_ERROR_MESSAGE, "A file with the given filename already exists!");
        } else {
            try {
                if (!this.fileService.insertFile(userId, file)) {
                    model.addAttribute(MODEL_ATTRIBUTE_ERROR_MESSAGE, "X");
                }
            } catch (IOException exc) {
                model.addAttribute(MODEL_ATTRIBUTE_ERROR_MESSAGE, exc.getMessage());
            }
        }
        return "result";
    }

    // delete file by userid and fileid.
    // needs application property spring.mvc.hiddenmethod.filter.enabled=true
    @DeleteMapping("file/{fileId}")
    public String deleteFile(@PathVariable("fileId") int fileId, Model model, Authentication authentication) {
        int userId = this.userService.getUserId(authentication.getName());
        if (!this.fileService.deleteFile(userId, fileId)) {
            model.addAttribute(MODEL_ATTRIBUTE_ERROR_MESSAGE, "X");
        }
        return "result";
    }

    // create or update a note
    @PostMapping("note")
    public String postNote(NotePayload payload, Model model, Authentication authentication) {
        int userId = this.userService.getUserId(authentication.getName());
        boolean operationOk;
        if (payload.getNoteId().isEmpty()) {
            // new note
            operationOk = this.noteService.insertNote(userId, payload);
        } else {
            // update existing note
            operationOk = this.noteService.updateNote(userId, payload);
        }
        if (!operationOk) {
            model.addAttribute(MODEL_ATTRIBUTE_ERROR_MESSAGE, "X");
        }
        return "result";
    }

    // delete note by userid and fileid
    @DeleteMapping("note/{noteId}")
    public String deleteNote(@PathVariable("noteId") int noteId, Model model, Authentication authentication) {
        int userId = this.userService.getUserId(authentication.getName());
        if (!this.noteService.deleteNote(userId, noteId)) {
            model.addAttribute(MODEL_ATTRIBUTE_ERROR_MESSAGE, "X");
        }
        return "result";
    }

    // create or update a credential
    @PostMapping("credential")
    public String postCredential(CredentialPayload payload, Model model, Authentication authentication) {
        int userId = this.userService.getUserId(authentication.getName());
        boolean operationOk;
        if (payload.getCredentialId().isEmpty()) {
            // new note
            operationOk = this.credentialService.insertCredential(userId, payload);
        } else {
            // update existing note
            operationOk = this.credentialService.updateCredential(userId, payload);
        }
        if (!operationOk) {
            model.addAttribute(MODEL_ATTRIBUTE_ERROR_MESSAGE, "X");
        }
        return "result";
    }

    // delete credential by userid and credentialid
    @DeleteMapping("credential/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") int credentialId, Model model, Authentication authentication) {
        int userId = this.userService.getUserId(authentication.getName());
        if (!this.credentialService.deleteCredential(userId, credentialId)) {
            model.addAttribute(MODEL_ATTRIBUTE_ERROR_MESSAGE, "X");
        }
        return "result";
    }
}
