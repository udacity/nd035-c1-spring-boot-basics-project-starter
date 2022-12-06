package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {

    private Integer credentialId;
    private int userid;
    private String url;
    private String username;
    private String key;
    private String password;
    public Credential() {}

    public Credential(
    Integer credentialId,
    int userId,
    String url,
    String username,
    String key,
    String password) {
        this.credentialId = credentialId;
        this.userid = userId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public int getUserId() {
        return userid;
    }

    public void setUserId(int userId) {
        this.userid = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
