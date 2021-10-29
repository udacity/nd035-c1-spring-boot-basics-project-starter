package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public void uploadFile(Integer userId, MultipartFile uploadFile) {
        System.out.println("Trying to add new file...");
        try {
            File newFile = new File(
                    null,
                    uploadFile.getOriginalFilename(),
                    uploadFile.getContentType(),
                    Long.toString(uploadFile.getSize()),
                    userId,
                    uploadFile.getBytes()
            );
            fileMapper.insert(newFile);
        } catch (MaxUploadSizeExceededException | IOException e) {
            e.getLocalizedMessage();
        }
    }

    public void deleteFile(Integer fileId) {
        fileMapper.delete(fileId);
    }

    public Boolean isFileInDatabase(String fileName) {
        return fileMapper.getFileByName(fileName) != null;
    }

    public File getFileById(Integer fileId) {
        return fileMapper.getFileById(fileId);
    }

    public List<File> getFilesList(Integer userid) {
        System.out.println("Fetching files list from database with size: " + fileMapper.getAllFilesForUser(userid).size());
        return fileMapper.getAllFilesForUser(userid);
    }

}
