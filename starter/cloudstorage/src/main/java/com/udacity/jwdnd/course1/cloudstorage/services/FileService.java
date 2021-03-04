package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper){
        this.fileMapper=fileMapper;
    }

    public boolean checkAndUploadFile(File file){
        if (this.fileMapper.getCountByFilename(file)>0){
            return false;
        } else {
            return this.fileMapper.upload(file) == 1;
        }
    }

    public File upload(User user, MultipartFile multipartfile) {
        String fileName = StringUtils.cleanPath(multipartfile.getOriginalFilename());
        File file = null;
        try {
            file = new File(null, fileName, multipartfile.getContentType(), String.valueOf(multipartfile.getSize()), user.getUserid(), multipartfile.getBytes());
        } catch (IOException e ){
            System.out.println(e.getMessage());
        }
        return file;
    }

    public ArrayList<File> getFiles(User user){
        return this.fileMapper.getAllFiles(user);
    }

    public Integer getFilesCount(User user){
        return this.fileMapper.getFilesCount(user);
    }

    public Boolean deleteFile(Integer currentUser, Integer fileId) {
        return this.fileMapper.deleteFile(currentUser,fileId)==1;
    }

    public File downloadFile(Integer currentUser, Integer fileId) {
        return this.fileMapper.getFileById(currentUser, fileId);
    }
}
