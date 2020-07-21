package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialForm {
    private int credentialId;
    private String url;
    private String username;
    private String key;
    private String password;
    private int userId;
    private String credentialAction;

    public CredentialForm() {
    }

    public int getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        if ( ! credentialId.isEmpty())
            this.credentialId = Integer.parseInt(credentialId);
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCredentialAction() {
        return credentialAction;
    }

    public void setCredentialAction(String credentialAction) {
        this.credentialAction = credentialAction;
    }
}
