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

    public boolean addCredential(Integer userId, CredentialForm credentialForm) {
        System.out.println("Trying to add new credential...");
        String randomKey = createRandomKey();
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), randomKey);

        if (isCredentialInDatabase(credentialForm.getUrl(), credentialForm.getUserName())) {
            return false;
        } else {
            Credential newCredential = new Credential(
                    null,
                    credentialForm.getUrl(),
                    credentialForm.getUserName(),
                    randomKey,
                    encryptedPassword,
                    userId
            );
            credentialMapper.insert(newCredential);
            return true;
        }
    }

    public Boolean editCredential(CredentialForm credentialForm) {
        System.out.println("Editing credential with id:" + credentialForm.getCredentialId());
        Credential editCredential = credentialMapper.getCredentialById(credentialForm.getCredentialId());
        String key = editCredential.getKey();

        boolean hasUrlChanged = false;
        boolean hasUserNameChanged = false;
        boolean hasPasswordChanged = false;

         if (!editCredential.getUrl().equals(credentialForm.getUrl())) {
            editCredential.setUrl(credentialForm.getUrl());
            hasUrlChanged = true;
        }
        if (!editCredential.getUserName().equals(credentialForm.getUserName())) {
            editCredential.setUserName(credentialForm.getUserName());
            hasUserNameChanged = true;
        }
        String decryptedPass = encryptionService.decryptValue(editCredential.getPassword(), key);
        String newPassword = credentialForm.getPassword();
        if (!decryptedPass.equals(newPassword)) {
            String newKey = createRandomKey();
            String newEncryptedPass = encryptionService.encryptValue(newPassword, newKey);
            editCredential.setKey(newKey);
            editCredential.setPassword(newEncryptedPass);
            hasPasswordChanged = true;
        }

        if (hasUrlChanged || hasUserNameChanged || hasPasswordChanged) {
            credentialMapper.updateCredential(editCredential);
            return true;
        } else {
            return false;
        }
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
