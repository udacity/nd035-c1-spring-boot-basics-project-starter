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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String addNote(RedirectAttributes redirectAttributes, Model model, @ModelAttribute Note note) {

        if (note.getNoteId() != null) {
            boolean isNoteUpdated = noteService.updateNote(note) != 0;
            buildSuccessAttributes(redirectAttributes, "Note updated successfully");
        } else {
            boolean isNoteAdded = noteService.addNote(note) != 0;
            buildSuccessAttributes(redirectAttributes, "Note added successfully");
        }
        return "redirect:/home";
    }

    @GetMapping(path = "/note/{noteId}/delete")
    public String deleteNote(RedirectAttributes redirectAttributes, Model model, @PathVariable("noteId") String noteId) {

        boolean isNoteDeleted = noteService.deleteNote(Integer.parseInt(noteId)) != 0;
        if (isNoteDeleted) {
            buildSuccessAttributes(redirectAttributes, "Note deleted successfully");
        } else {
            buildErrorAttributes(redirectAttributes, "Can not delete note");
        }
        return "redirect:/home";
    }

    @PostMapping(path = "/credential")
    public String addCredential(RedirectAttributes redirectAttributes, Model model, @ModelAttribute Credential credential) {

        if (credential.getCredentialId() != null) {
            boolean isCredentialUpdated = credentialService.updateCredential(credential) != 0;
            if (isCredentialUpdated) {
                buildSuccessAttributes(redirectAttributes, "Credential updated successfully");
            } else {
                buildErrorAttributes(redirectAttributes, "Can not update note");
            }
        } else {
            boolean isCredentialAdded = credentialService.addCredential(credential) != 0;
            if (isCredentialAdded) {
                buildSuccessAttributes(redirectAttributes, "Credential added successfully");
            } else {
                buildErrorAttributes(redirectAttributes, "Can not add credential");
            }
        }
        return "redirect:/home";
    }

    @GetMapping(path = "/credential/{credentialId}/delete")
    public String deleteCredential(RedirectAttributes redirectAttributes, Model model, @PathVariable("credentialId") String credentialId) {

        boolean isCredentialDeleted = credentialService.deleteCredential(Integer.parseInt(credentialId)) != 0;
        if (isCredentialDeleted) {
            buildSuccessAttributes(redirectAttributes, "Credential deleted successfully");
        } else {
            buildErrorAttributes(redirectAttributes, "Can not delete credential");
        }
        return "redirect:/home";
    }

    @PostMapping(path = "/file")
    public String addFile(RedirectAttributes redirectAttributes, @RequestParam("fileUpload") MultipartFile fileUpload) throws IOException {

        File file = new File();
        file.setFileName(fileUpload.getOriginalFilename());
        file.setContentType(fileUpload.getContentType());
        file.setFileSize(String.valueOf(fileUpload.getSize()));
        InputStream inputStream = fileUpload.getInputStream();
        file.setFileData(inputStream.readAllBytes());
        if (StringUtils.isEmpty(file.getFileName()) || file.getFileData().length == 0) {
            buildErrorAttributes(redirectAttributes, "There is no file to upload");
        } else {
            boolean isFileAdded = fileService.addFile(file) != 0;
            if (isFileAdded) {
                buildSuccessAttributes(redirectAttributes, "File added successfully");
            } else {
                buildErrorAttributes(redirectAttributes, "Can not upload file, duplicate File Name");
            }
        }
        return "redirect:/home";
    }

    @GetMapping(path = "/file/{fileId}/view")
    public ResponseEntity<byte[]> getFile(@PathVariable("fileId") String fileId) throws IOException {
        File file = fileService.getFileByFileId(Integer.parseInt(fileId));
        if (file != null) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getFileName()).build());
            return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.parseMediaType(file.getContentType())).body(file.getFileData());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/file/{fileId}/delete")
    public String deleteFile(RedirectAttributes redirectAttributes, Model model, @PathVariable("fileId") String fileId) {

        boolean isFileDeleted = fileService.deleteFile(Integer.parseInt(fileId)) != 0;
        if (isFileDeleted) {
            buildSuccessAttributes(redirectAttributes, "File deleted successfully");
        } else {
            buildErrorAttributes(redirectAttributes, "Can not delete file");
        }
        return "redirect:/home";
    }

    private void buildErrorAttributes(RedirectAttributes redirectAttributes, String errorMess) {
        redirectAttributes.addFlashAttribute("isError", true);
        redirectAttributes.addFlashAttribute("errorMess", errorMess);
    }

    private void buildSuccessAttributes(RedirectAttributes redirectAttributes, String successMess) {
        redirectAttributes.addFlashAttribute("isSuccess", true);
        redirectAttributes.addFlashAttribute("successMess", successMess);
    }
}
