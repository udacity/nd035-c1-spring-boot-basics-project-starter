package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    @Autowired
    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public void saveFile(MultipartFile multipartFile, long userId) throws IOException {
        File file = new com.udacity.jwdnd.course1.cloudstorage.models.File();

        file.setFilename(multipartFile.getOriginalFilename());
        file.setContenttype(multipartFile.getContentType());
        file.setFilesize(String.valueOf(multipartFile.getSize()));

        if (userId < Integer.MIN_VALUE || userId > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Could not convert userid." + userId);
        }
        Integer id = (int) userId;

        file.setUserid(id);
        file.setFiledata(multipartFile.getBytes());

        fileMapper.insertFile(file);
    }

    public File getFileById(Integer id) {
        return fileMapper.getFileById(id);
    }

    public void deleteFileById(Integer id) {
        fileMapper.deleteFileById(id);
    }

    public List<File> getAllFiles(long userId) throws IOException {
        if (userId < Integer.MIN_VALUE || userId > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Could not convert userid." + userId);
        }
        Integer id = (int) userId;

        return fileMapper.getFilesByUserId(id);
    }
}
