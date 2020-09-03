package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
public class CredentialService {

    @Autowired
    private UserService userService;

    @Autowired
    private EncryptionService encryptionService;

    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public List<CredentialForm> getCredentials() {
        User user = userService.getUser(UserService.getLoggedInUsername());

        List<Credential> credentialList = credentialMapper.getCredentials(user.getUserId());

        List<CredentialForm> credentialFormList = new ArrayList<>();
        for (Credential credential: credentialList) {
            CredentialForm credentialForm = new CredentialForm(null, null, null, null, null, null);
            credentialForm.setCredentialId(credential.getCredentialId());
            credentialForm.setUrl(credential.getUrl());
            credentialForm.setUsername(credential.getUsername());
            credentialForm.setPassword(credential.getPassword());
            credentialForm.setUserId(credential.getUserId());
            String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
            credentialForm.setDecryptedPassword(decryptedPassword);
            credentialFormList.add(credentialForm);
        }


//        return credentialMapper.getCredentials(user.getUserId());
        return credentialFormList;
    }

    public Message addCredential(CredentialForm credentialForm) {

        String username = UserService.getLoggedInUsername();
        User user = userService.getUser(username);

        Credential credential = new Credential();

        Map<String,String> encryptedPasswordAndKey = getEncryptedPasswordAndKey(credentialForm.getPassword());

        credential.setUrl(credentialForm.getUrl());
        credential.setUsername(credentialForm.getUsername());
        credential.setPassword(encryptedPasswordAndKey.get("password"));
        credential.setKey(encryptedPasswordAndKey.get("key"));
        credential.setUserId(user.getUserId());

        Integer insertedValue = credentialMapper.insert(credential);

        if (insertedValue > 0) {
            return new Message(false, true, "Credential successfully added");
        }

        return new Message(true, false, "Error adding credential.");


    }

    public Message editCredential(CredentialForm credentialForm) {

        String username = UserService.getLoggedInUsername();
        User user = userService.getUser(username);

        if (user.getUserId().equals(credentialForm.getUserId())) {
            Credential credential = new Credential();

            Map<String,String> encryptedPasswordAndKey = getEncryptedPasswordAndKey(credentialForm.getPassword());

            credential.setCredentialId(credentialForm.getCredentialId());
            credential.setUrl(credentialForm.getUrl());
            credential.setUsername(credentialForm.getUsername());
            credential.setPassword(encryptedPasswordAndKey.get("password"));
            credential.setKey(encryptedPasswordAndKey.get("key"));
            credential.setUserId(user.getUserId());

            credentialMapper.update(credential);

            System.out.println(credential);

            return new Message(false, true, "Credential update was successful");
        }

        return new Message(true, false, "You are not authorized to edit.");


    }

    public Message deleteCredential(Integer id) {

        String username = UserService.getLoggedInUsername();
        User user = userService.getUser(username);

        Credential credential = credentialMapper.getCredential(id);

        if (credential.getUserId().equals(user.getUserId())) {

            credentialMapper.deleteCredential(id);

            return new Message(false, true, "Delete credential was successful");

        }

        return new Message(true, false, "Unable to delete credential. Please make sure you have the right authorization");
    }

    public Map<String, String> getEncryptedPasswordAndKey(String password) {

        Map<String, String> value = new HashMap<>();
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);

        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

        value.put("password", encryptedPassword);
        value.put("key", encodedKey);
        return value;
    }
}
