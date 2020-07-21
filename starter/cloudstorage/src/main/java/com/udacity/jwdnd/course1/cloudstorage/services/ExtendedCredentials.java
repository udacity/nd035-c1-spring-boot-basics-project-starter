package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;

public class ExtendedCredentials extends Credentials {
    private String decryptedPassword;

    public ExtendedCredentials(Credentials credentials) {
        this.setCredentialid(credentials.getCredentialid());
        this.setKey(credentials.getKey());
        this.setPassword(credentials.getPassword());
        this.setUrl(credentials.getUrl());
        this.setUserid(credentials.getUserid());
        this.setUsername(credentials.getUsername());
    }

    public String getDecryptedPassword() {
        return decryptedPassword;
    }

    public void setDecryptedPassword(String decryptedPassword) {
        this.decryptedPassword = decryptedPassword;
    }
}
