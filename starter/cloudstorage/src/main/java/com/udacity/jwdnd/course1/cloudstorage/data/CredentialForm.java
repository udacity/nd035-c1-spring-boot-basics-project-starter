package com.udacity.jwdnd.course1.cloudstorage.data;

public class CredentialForm {

    private Integer credentialId;
    private String url;
    private String username;
    private String password;
    private String decryptedPassword;
    private Integer userId;

    public CredentialForm(Integer credentialId, String url, String username, String password, String decryptedPassword, Integer userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.password = password;
        this.decryptedPassword = decryptedPassword;
        this.userId = userId;
    }

    public String getDecryptedPassword() {
        return decryptedPassword;
    }

    public void setDecryptedPassword(String decryptedPassword) {
        this.decryptedPassword = decryptedPassword;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CredentialForm{" +
                "credentialId=" + credentialId +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", decryptedPassword='" + decryptedPassword + '\'' +
                ", userId=" + userId +
                '}';
    }
}
