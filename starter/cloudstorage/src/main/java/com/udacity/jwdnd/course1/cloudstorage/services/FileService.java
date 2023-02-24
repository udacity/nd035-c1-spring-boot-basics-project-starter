package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.security.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private UserDetailService userDetailService;

    public List<File> getFilesByCurrentUserId() {
        return fileMapper.getFilesByUserId(userDetailService.getCurrentUserId());
    }

    public int addFile(File file) {
        return fileMapper.insertFile(file.getFileName(), file.getContentType(), file.getFileSize(), userDetailService.getCurrentUserId(), file.getFileData());
    }

    public File getFileByFileId(Integer fileId) {
        return fileMapper.getFilesByFileIdAndUserId(fileId, userDetailService.getCurrentUserId());
    }

    public int deleteFile(Integer fileId) {
        return fileMapper.deleteFile(fileId);
    }
}
