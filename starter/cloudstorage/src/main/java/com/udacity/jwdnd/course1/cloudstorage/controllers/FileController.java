package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/files")
public class FileController {
    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/view/{fileId}")
    public ResponseEntity<byte[]> getFilesId(@PathVariable("fileId") Integer fileId, Authentication authentication) {
        User currentUser = userService.getUser(authentication.getName());
        File file=fileService.downloadFile(currentUser.getUserid(), fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file.getFiledata());
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFileId(@PathVariable("fileId") Integer fileId, Authentication authentication){
        User currentUser = userService.getUser(authentication.getName());
        Boolean status=fileService.deleteFile(currentUser.getUserid(), fileId);
        if (status) {
            return "redirect:/result?success&message=File successfully deleted!";
        } else {
            return "redirect:/result?error&message=Unable to delete file! Please try again.";
        }
    }


    @PostMapping("/upload")
    public String handleFileUpload(Authentication authentication,@RequestParam("fileUpload") MultipartFile multipartfile) {

        if (multipartfile.isEmpty()) {
            System.out.println("Empty file should log error!");
        }
        User currentUser = userService.getUser(authentication.getName());
        File file = fileService.upload(currentUser, multipartfile);
        Boolean status = fileService.checkAndUploadFile(file);
        if (status) {
            return "redirect:/result?success&message=File successfully uploaded!";
        } else {
            return "redirect:/result?error&message=Unable to upload file! Duplicate filename!";
        }
    }

}
