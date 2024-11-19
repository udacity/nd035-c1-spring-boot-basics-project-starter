package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    @Autowired
    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }


    @PostMapping("/fileUpload")
    public String fileUpload(@RequestParam("fileUpload") MultipartFile file, Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = (String) authentication.getPrincipal();
            User user = userService.getUserByUsername(username);
            Long userId = user.getId();
            fileService.saveFile(file, userId);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Could not parse file.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/home";
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> viewFile(@PathVariable("id") Integer fileId) {
        // Retrieve the file from the service
        File file = fileService.getFileById(fileId);

        if (file == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, file.getContenttype());

        return new ResponseEntity<>(file.getFiledata(), headers, HttpStatus.OK);
    }
    @GetMapping("/files/delete/{id}")
    public String deleteFile(@PathVariable("id") Integer fileId, RedirectAttributes redirectAttributes) {
        try {
            fileService.deleteFileById(fileId);
            redirectAttributes.addFlashAttribute("successMessage", "File deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while deleting the file: " + e.getMessage());
        }

        return "redirect:/home";
    }
}
