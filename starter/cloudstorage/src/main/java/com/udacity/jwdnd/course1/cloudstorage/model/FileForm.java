package com.udacity.jwdnd.course1.cloudstorage.model;

import java.io.File;
import java.sql.Blob;

public class FileForm {
    private int formId;
    private String filename;
    private String contentType;
    private String filesize;
    private int userId;
    private Blob filedata;
    private String credentialAction;
    private File fileUpload;

    public FileForm() {
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Blob getFiledata() {
        return filedata;
    }

    public void setFiledata(Blob filedata) {
        this.filedata = filedata;
    }

    public String getCredentialAction() {
        return credentialAction;
    }

    public void setCredentialAction(String credentialAction) {
        this.credentialAction = credentialAction;
    }

    public File getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(File fileUpload) {
        this.fileUpload = fileUpload;
    }
}
