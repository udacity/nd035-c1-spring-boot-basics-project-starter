package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.controller.CredentialPayload;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialEntity;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;
    private Random random = new SecureRandom();

    CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public boolean insertCredential(int userId, CredentialPayload credentialPayload) {
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialPayload.getPassword(), encodedKey);

        return this.credentialMapper.insertCredential(new CredentialEntity(
            credentialPayload.getUrl(),
            credentialPayload.getUsername(),
            encodedKey,
            encryptedPassword,
            userId
        )) > 0;
    }

    public boolean updateCredential(int userId, CredentialPayload credentialPayload) {
        int credentialId = Integer.parseInt(credentialPayload.getCredentialId());
        CredentialEntity entity = this.credentialMapper.getCredentialByUseridAndCredentialId(userId, credentialId);

        if (entity == null) return false;

        String encodedKey = entity.getKey();
        String encryptedPassword = encryptionService.encryptValue(credentialPayload.getPassword(), encodedKey);

        return this.credentialMapper.updateCredential(new CredentialEntity(
            credentialId,
            credentialPayload.getUrl(),
            credentialPayload.getUsername(),
            encodedKey,
            encryptedPassword,
            userId
        )) > 0;
    }

    public List<CredentialPayload> getCredentials(int userid) {
        List<CredentialPayload> credentialPayloads = new ArrayList<>();
        this.credentialMapper.getCredentialsByUserid(userid)
            .stream().forEach(entity -> {
            String originalPassword = encryptionService.decryptValue(entity.getPassword(), entity.getKey());
            CredentialPayload payload = new CredentialPayload(
                String.valueOf(entity.getCredentialId()),
                entity.getUrl(),
                entity.getUsername(),
                originalPassword,
                entity.getPassword()
            );
            credentialPayloads.add(payload);
        });
        return credentialPayloads;
    }

    public boolean deleteCredential(int userId, int credentialId) {
        return this.credentialMapper.deleteCredentialByUserIdAndCredentialId(userId, credentialId) > 0;
    }
}
