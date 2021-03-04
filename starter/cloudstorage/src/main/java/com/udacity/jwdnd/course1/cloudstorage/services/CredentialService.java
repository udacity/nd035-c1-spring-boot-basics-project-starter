package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;


    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public boolean create(Credential credential) {
        return this.credentialMapper.create(credential)==1;
    }

    public boolean update(Credential credential){
        return this.credentialMapper.update(credential)==1;
    }

    public List<Credential> getCredentials(User user){
        List<Credential> credential = credentialMapper.getAll(user);
        return credential.stream().map(this::decryptPassword).collect(Collectors.toList());
    }

    public Boolean deleteCredential(Integer currentUser, Integer credentialid) {
        return this.credentialMapper.delete(currentUser,credentialid)==1;
    }

    public Credential decryptPassword(Credential credential) {
        credential.setDecryptedPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        return credential;
    }
}
