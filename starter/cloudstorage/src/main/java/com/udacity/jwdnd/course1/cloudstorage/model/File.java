package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {
    private Integer fileId;
    private int userId;
    private String filename;
    private String contentType;
    private String fileSize;
    private byte[] data;

    public File() { }
    public File(Integer fileId, int userId, String filename, String contentType, String fileSize, byte[] data) {
        this.fileId = fileId;
        this.userId = userId;
        this.filename = filename;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.data = data;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
