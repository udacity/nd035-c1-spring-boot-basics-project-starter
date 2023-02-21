package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialResponse;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.security.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CredentialService {

    @Autowired
    private CredentialMapper credentialMapper;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private EncryptionService encryptionService;

    public List<Credential> getCredentialsByCurrentUserId() {

        return credentialMapper.getCredentialsByUserId(userDetailService.getCurrentUserId());
    }

    public int addCredential(Credential credential) {
        String key = userDetailService.generateRandomKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), key);
        return credentialMapper.insertCredential(credential.getUrl(), credential.getUsername(), key, encryptedPassword, userDetailService.getCurrentUserId());
    }

    public List<CredentialResponse> buildCredentialsResponses(List<Credential> credentials) {
        List<CredentialResponse> credentialResponses = new ArrayList<>();
        credentials.forEach(credential -> {
            String decryptedPassword = getDecryptedPassword(credential.getKey(), credential.getPassword());
            credentialResponses.add(new CredentialResponse(credential, decryptedPassword));
        });
        return credentialResponses;
    }

    private String getDecryptedPassword(String key, String encryptedPassword) {
        return encryptionService.decryptValue(encryptedPassword, key);
    }
}
