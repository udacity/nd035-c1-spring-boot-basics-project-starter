package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credentials {
    private int credentialid;
    private String url;
    private String username;
    private String key;
    private String password;
    private int userid;

    public Credentials() {
        credentialid = 0;
    }

    public Credentials(String url, String username, String key, String password, int userid) {
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userid = userid;
    }

    public int getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(int credentialid) {
        this.credentialid = credentialid;
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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
