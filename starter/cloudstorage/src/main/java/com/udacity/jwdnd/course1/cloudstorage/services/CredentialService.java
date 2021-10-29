package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;

    }

    public Integer addCredential(Integer userId, CredentialForm credentialForm) {
        System.out.println("Trying to add new credential...");
        String randomKey = createRandomKey();
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), randomKey);

        Credential newCredential = new Credential(
                null,
                credentialForm.getUrl(),
                credentialForm.getUserName(),
                randomKey,
                encryptedPassword,
                userId
        );

        return credentialMapper.insert(newCredential);
    }

    public void editCredential(CredentialForm credentialForm) {
        System.out.println("Editing credential with id:" + credentialForm.getCredentialId());
        Credential editCredential = credentialMapper.getCredentialById(credentialForm.getCredentialId());
        String key = editCredential.getKey();

        if (!editCredential.getUrl().equals(credentialForm.getUrl())) {
            editCredential.setUrl(credentialForm.getUrl());
        }
        if (!editCredential.getUserName().equals(credentialForm.getUserName())) {
            editCredential.setUserName(credentialForm.getUserName());
        }
        String decryptedPass = encryptionService.decryptValue(editCredential.getPassword(), key);
        String newPassword = credentialForm.getPassword();
        if (!decryptedPass.equals(newPassword)) {
            String newKey = createRandomKey();
            String newEncryptedPass = encryptionService.encryptValue(newPassword, newKey);
            editCredential.setKey(newKey);
            editCredential.setPassword(newEncryptedPass);
        }

        credentialMapper.updateCredential(editCredential);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.delete(credentialId);
    }

    public Credential getCredentialById(Integer credentialId) {
        return credentialMapper.getCredentialById(credentialId);
    }

    public Boolean isCredentialInDatabase(String url, String userName) {
        return credentialMapper.getCredentialByContent(url, userName) != null;
    }

    public List<Credential> getCredentialListForUser(Integer userid) {
        System.out.println("Fetching credentials list from database with size: " + credentialMapper.getAllCredentialsForUser(userid).size());
        List<Credential> credentials = credentialMapper.getAllCredentialsForUser(userid);
        for (Credential credential : credentials) {
            String encryptedPassword = credential.getPassword();
            String decryptedPassword = encryptionService.decryptValue(encryptedPassword, credential.getKey());
            credential.setDecryptedPassword(decryptedPassword);
        }
        return credentials;
    }

    private String createRandomKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);

        return Base64.getEncoder().encodeToString(key);
    }
}
