package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

@Service
public class FileService {


    @Autowired
    private UserService userService;

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getFiles() {
        User user = userService.getUser(UserService.getLoggedInUsername());
        return fileMapper.select(user.getUserId());
    }

    public Message uploadFile(FileForm file)  {
        String filename = StringUtils.cleanPath(file.getFileUpload().getOriginalFilename());

        Message message = new Message();

        if (file.getFileUpload().isEmpty()) {
            message.setError(true);
            message.setSuccess(false);
            message.setMsg("Failed to store empty file.");

            return message;

        }

        if (filename.contains("..")) {
            message.setError(true);
            message.setSuccess(false);
            message.setMsg("Cannot store file with relative path outside current directory " + filename);
            return message;
        }

        String username = UserService.getLoggedInUsername();
        User user = userService.getUser(username);

        List<File> fileList = fileMapper.select(user.getUserId());

        for (File fileName: fileList) {
            if (fileName.getFileName().equals(filename)) {
                message.setError(true);
                message.setSuccess(false);
                message.setMsg("File already exists: " + filename);
                return message;
            }
        }

        File fileSave = new File();
        fileSave.setFileName(filename);
        fileSave.setContentType(file.getFileUpload().getContentType());

        fileSave.setFileSize("" + file.getFileUpload().getSize());

        try {
            fileSave.setFileData(file.getFileUpload().getBytes());
        } catch (IOException e) {
            message.setError(true);
            message.setSuccess(false);
            message.setMsg("Error uploading file " + e.getMessage());

            e.printStackTrace();
        }


        fileSave.setUserId(user.getUserId());

        fileMapper.insert(fileSave);

        message.setError(false);
        message.setSuccess(true);
        message.setMsg("File upload successful!");

        return message;


    }

    public Message deleteFile(Integer fileId) {

        Message message = new Message();

        File file = fileMapper.getFileById(fileId);

        if (file == null) {
            message.setError(true);
            message.setSuccess(false);
            message.setMsg("File does not exist");

            return message;
        }

        User user = userService.getUser(UserService.getLoggedInUsername());

        if (file.getUserId().equals(user.getUserId())) {
            fileMapper.delete(fileId);
            message.setError(false);
            message.setSuccess(true);
            message.setMsg("File successfully deleted");

            return message;
        }

        return message;
    }

    public File getFile(Integer id) {

        File file = fileMapper.getFileById(id);

        User user = userService.getUser(UserService.getLoggedInUsername());

        if (file != null && file.getUserId().equals(user.getUserId())) {

            return file;
        }

        return null;

    }

 }
