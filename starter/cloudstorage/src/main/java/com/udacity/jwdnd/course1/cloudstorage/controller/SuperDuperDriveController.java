package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialResponse;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class SuperDuperDriveController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private FileService fileService;

    @GetMapping(path = "/home")
    public String getHomePage(Model model) {
        List<Note> notes = noteService.getNotesByCurrentUserId();
        List<Credential> credentials = credentialService.getCredentialsByCurrentUserId();
        List<CredentialResponse> credentialResponses = credentialService.buildCredentialsResponses(credentials);
        List<File> files = fileService.getFilesByCurrentUserId();

        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentialResponses);
        model.addAttribute("files", files);
        return "home";
    }

    @PostMapping(path = "/note")
    public String addNote(Model model, @ModelAttribute Note note) {

        if (note.getNoteId() != null) {
            boolean isNoteUpdated = noteService.updateNote(note) != 0;
        } else {
            boolean isNoteAdded = noteService.addNote(note) != 0;
        }
        return "redirect:/home";
    }

    @GetMapping(path = "/note/{noteId}/delete")
    public String deleteNote(Model model, @PathVariable("noteId") String noteId) {

        boolean isNoteDeleted = noteService.deleteNote(Integer.parseInt(noteId)) != 0;
        return "redirect:/home";
    }

    @PostMapping(path = "/credential")
    public String addCredential(Model model, @ModelAttribute Credential credential) {

        if (credential.getCredentialId() != null) {
            boolean isCredentialUpdated = true;
        } else {
            boolean isCredentialAdded = credentialService.addCredential(credential) != 0;
        }
        return "redirect:/home";
    }

    @GetMapping(path = "/credential/{credentialId}/delete")
    public String deleteCredential(Model model, @PathVariable("credentialId") String credentialId) {

        boolean isCredentialDeleted = credentialService.deleteCredential(Integer.parseInt(credentialId)) != 0;
        return "redirect:/home";
    }

    @PostMapping(path = "/file")
    public String addFile(Model model, @RequestParam("fileUpload") MultipartFile fileUpload) throws IOException {

        File file = new File();
        file.setFileName(fileUpload.getOriginalFilename());
        file.setContentType(fileUpload.getContentType());
        file.setFileSize(String.valueOf(fileUpload.getSize()));
        InputStream inputStream = fileUpload.getInputStream();
        file.setFileData(inputStream.readAllBytes());
        boolean isFileAdded = fileService.addFile(file) != 0;
        return "redirect:/home";
    }

    @GetMapping(path = "/file/{fileId}/view")
    public ResponseEntity<byte[]> getFile(@PathVariable("fileId") String fileId) throws IOException {
        File file = fileService.getFileByFileId(Integer.parseInt(fileId));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getFileName()).build());
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.parseMediaType(file.getContentType())).body(file.getFileData());
    }
}
