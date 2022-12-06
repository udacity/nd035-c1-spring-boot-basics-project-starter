package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {

    private final EncryptionService encryptionService;
    private final CredentialsMapper credentialsMapper;

    public CredentialService(EncryptionService encryptionService, CredentialsMapper credentialsMapper) {
        this.encryptionService = encryptionService;
        this.credentialsMapper = credentialsMapper;
    }

    public Credential createCredential(String url, String username, String password, int userId) {
        Credential credential = new Credential();
        credential.setUserId(userId);
        credential.setUrl(url);
        credential.setUsername(username);

        String encodedKey = generateEncodedKey();
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword(password, encodedKey));

        credentialsMapper.insert(credential);
        return credential;
    }

    public void updateCredential(
            String credentialId,
            String newUsername,
            String newUrl,
            String password) {

        Credential existingCredential = credentialsMapper.getCredentialById(Integer.parseInt(credentialId));
        existingCredential.setUsername(newUsername);
        existingCredential.setUrl(newUrl);
        existingCredential.setCredentialId(Integer.parseInt(credentialId));
        existingCredential.setPassword(encryptedPassword(password, existingCredential.getKey()));

        credentialsMapper.updateCredential(existingCredential);
    }

    public void deleteCredential(int credentialId) {
        credentialsMapper.deleteCredential(credentialId);
    }

    public List<Credential> getUserCredentials(int userId) {
        return credentialsMapper.getCredentials(userId).stream().map(credential -> {
            Credential newCredential = new Credential(
                    credential.getCredentialId(),
                    credential.getUserId(),
                    credential.getUrl(),
                    credential.getUsername(),
                    credential.getKey(),
                    credential.getPassword());

            newCredential.setPassword(decryptedPassword(credential.getPassword(), credential.getKey()));
            return newCredential;
        }).collect(Collectors.toList());
    }

   /* private Credential wrapCredential(Credential c) {
        Credential mapped =  new Credential(c.getCredentialId(), c.getUrl(), c.getUsername(),
                null, c.getPassword(), c.getUserId());
        mapped.setPassword(decryptedPassword(c.getPassword(), c.getKey()));
        return mapped;
    }*/

    private String generateEncodedKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    private String encryptedPassword(String password, String encodedKey) {
        return encryptionService.encryptValue(password, encodedKey);
    }

    private String decryptedPassword(String encryptedPassword, String encodedKey) {
        return encryptionService.decryptValue(encryptedPassword, encodedKey);
    }
}
