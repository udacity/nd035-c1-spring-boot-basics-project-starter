package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.data.File;
import com.udacity.jwdnd.course1.cloudstorage.data.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.data.Message;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


@Controller
public class FileController {

    @Autowired
    private FileService fileService;


    @PostMapping("/fileupload")
    public String fileUpload(FileForm file, Model model) {

       Message message = fileService.uploadFile(file);

        model.addAttribute("message", message.getMsg());
        model.addAttribute("success", message.isSuccess());
        model.addAttribute("error", message.isError());

        return "result";
    }

    @GetMapping("/file/delete")
    public String fileDelete(@RequestParam("id") Integer id, Model model) {

        Message message = fileService.deleteFile(id);

        model.addAttribute("message", message.getMsg());
        model.addAttribute("success", message.isSuccess());
        model.addAttribute("error", message.isError());

        return "result";
    }

    @GetMapping("/file/download")
    public void fileDownload(@RequestParam("id") Integer id, HttpServletResponse response, Model model) {

        File file = fileService.getFile(id);
        if (file != null) {
            response.setContentType("application/" + file.getContentType());
            response.addHeader("Content-Disposition", "attachment; filename="+file.getFileName());
            try(InputStream in = new ByteArrayInputStream(file.getFileData());
                OutputStream out = response.getOutputStream()) {

                byte[] buffer = new byte[Integer.parseInt(file.getFileSize())];

                int numBytesRead;
                while ((numBytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, numBytesRead);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
//            try {
//                InputStream in = new ByteArrayInputStream(file.getFileData());
//                OutputStream out = response.getOutputStream();
//
//                byte[] buffer = new byte[Integer.parseInt(file.getFileSize())];
//
//                int numBytesRead;
//                while ((numBytesRead = in.read(buffer)) > 0) {
//                    out.write(buffer, 0, numBytesRead);
//                }
//
//                response.getOutputStream().flush();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
        }

    }



}
