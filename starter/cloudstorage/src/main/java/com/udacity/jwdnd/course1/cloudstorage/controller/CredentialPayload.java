package com.udacity.jwdnd.course1.cloudstorage.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CredentialPayload {
    private String credentialId;
    private String url;
    private String username;
    private String password;
    private String encryptedPassword;
}
