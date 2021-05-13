package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.controller.FilePayload;
import com.udacity.jwdnd.course1.cloudstorage.model.FileEntity;
import com.udacity.jwdnd.course1.cloudstorage.model.FileMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    FileMapper fileMapper;

    FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public boolean insertFile(int userId, MultipartFile file) throws IOException {
        return this.fileMapper.insertFile(new FileEntity(
            file.getOriginalFilename(),
            file.getContentType(),
            String.valueOf(file.getSize()),
            userId,
            file.getBytes()
        )) > 0;
    }

    public boolean filenameExists(int userId, String filename) {
        return !this.fileMapper.getFilesByUseridAndFilename(userId, filename).isEmpty();
    }

    public FileEntity getFile(int userId, int fileId) {
        return this.fileMapper.getFileByUserIdAndFileId(userId, fileId);
    }

    public List<FilePayload> getFiles(int userid) {
        return this.fileMapper.getFilesByUserid(userid)
            .stream()
            .map(FilePayload::fromEntity)
            .collect(Collectors.toList());
    }

    public boolean deleteFile(int userId, int fileId) {
        return this.fileMapper.deleteFileByUserIdAndFileId(userId, fileId) > 0;
    }
}
