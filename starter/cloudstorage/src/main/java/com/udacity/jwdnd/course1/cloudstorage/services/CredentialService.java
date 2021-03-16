package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CredentialService {

    private final EncryptionService encryptionService;
    private final CredentialsMapper credentialsMapper;
    private final UserMapper userMapper;

    public List<Credential> findCredentialsByUsername(String username) {
        val user = userMapper.findByUsername(username);
        return credentialsMapper.findByUserid(user.getUserId());
    }

    public Integer createCredentialsForUser(Credential credential, String username){
        val user = userMapper.findByUsername(username);

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);

        credential.setUserId(user.getUserId());
        return credentialsMapper.create(credential);
    }
}
