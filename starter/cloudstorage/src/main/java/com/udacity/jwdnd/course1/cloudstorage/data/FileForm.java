package com.udacity.jwdnd.course1.cloudstorage.data;

import org.springframework.web.multipart.MultipartFile;

public class FileForm {
    private MultipartFile fileUpload;

    public MultipartFile getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(MultipartFile fileUpload) {
        this.fileUpload = fileUpload;
    }

}
