package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public File getFile(int fileId) {
        return fileMapper.getFile(fileId);
    }

    public List<File> getAllFiles(int userId) {
        return fileMapper.getAllFiles(userId);
    }

    public void deleteFile(int fileId) {
        fileMapper.deleteFile(fileId);
    }

    public int createFile(MultipartFile fileUpload, int userId) throws IOException {
        File file = new File();

        file.setUserId(userId);
        file.setFilename(fileUpload.getOriginalFilename());
        file.setContentType(fileUpload.getContentType());
        file.setFileSize(Long.toString(fileUpload.getSize()));
        file.setData(fileUpload.getBytes());

        return fileMapper.insert(file);
    }

    public boolean fileExists(MultipartFile fileUpload) {
        return fileMapper.getFileByName(fileUpload.getOriginalFilename()) != null;
    }
}
