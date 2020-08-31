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

        if (file.getFileUpload().isEmpty()) {

            return new Message(true, false, "Failed to store empty file.");

        }

        if (filename.contains("..")) {
            return new Message(true, false, "Cannot store file with relative path outside current directory " + filename);
        }

        String username = UserService.getLoggedInUsername();
        User user = userService.getUser(username);

        List<File> fileList = fileMapper.select(user.getUserId());

        for (File fileName: fileList) {
            if (fileName.getFileName().equals(filename)) {
                return new Message(true, false, "File already exists: " + filename);
            }
        }

        File fileSave = new File();
        fileSave.setFileName(filename);
        fileSave.setContentType(file.getFileUpload().getContentType());

        fileSave.setFileSize("" + file.getFileUpload().getSize());

        try {
            fileSave.setFileData(file.getFileUpload().getBytes());
        } catch (IOException e) {

            e.printStackTrace();

            return new Message(true, false, "Error uploading file " + e.getMessage());

        }

        fileSave.setUserId(user.getUserId());

        fileMapper.insert(fileSave);

        return new Message(false, true, "File upload successful!");


    }

    public Message deleteFile(Integer fileId) {


        File file = fileMapper.getFileById(fileId);

        if (file == null) {

            return new Message(true, false, "File does not exist");
        }

        User user = userService.getUser(UserService.getLoggedInUsername());

        if (file.getUserId().equals(user.getUserId())) {

            return new Message(false, true, "File successfully deleted");
        }

        return new Message(true, false, "Unable to delete file.");
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
