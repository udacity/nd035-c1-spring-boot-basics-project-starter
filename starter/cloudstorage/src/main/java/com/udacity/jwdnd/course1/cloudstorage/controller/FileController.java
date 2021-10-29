package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
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
@RequestMapping
public class FileController {

    private final UserService userService;
    private final FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("file")
    public String uploadFileForUser(Authentication authentication, MultipartFile fileUpload, Model model) {
        Integer userId = userService.getUserId(authentication.getName());

        if (fileService.isFileInDatabase(fileUpload.getOriginalFilename())) {
            model.addAttribute("errorMessage", "The file already exists.");
        } else {
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
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
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
}
