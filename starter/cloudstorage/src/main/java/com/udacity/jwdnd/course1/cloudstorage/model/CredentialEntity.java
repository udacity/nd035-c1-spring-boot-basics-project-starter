package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CredentialEntity {
    private int credentialId;
    private final String url;
    private final String username;
    private final String key;
    private final String password;
    private final int userid;
}
