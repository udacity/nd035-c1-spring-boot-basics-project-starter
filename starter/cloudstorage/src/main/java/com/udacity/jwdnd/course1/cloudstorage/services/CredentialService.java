package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CredentialService {

  private final EncryptionService encryptionService;
  private final CredentialsMapper credentialsMapper;
  private final UserMapper userMapper;

  public List<Credential> findCredentialsByUsername(String username) {
    val user = userMapper.findByUsername(username);
    val credentials = credentialsMapper.findByUserid(user.getUserId());
    return credentials.stream()
        .map(
            credential -> {
              val decryptedValue =
                  encryptionService.decryptValue(credential.getPassword(), credential.getKey());
              credential.setDecryptedPassword(decryptedValue);
              return credential;
            })
        .collect(Collectors.toList());
  }

  public void createCredentialsForUser(Credential credential, String username) {
    val user = userMapper.findByUsername(username);

    String encodedKey = encryptionService.getRandomEncodingKey();
    String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
    credentialsMapper.create(
        Credential.builder()
            .key(encodedKey)
            .url(credential.getUrl())
            .userId(user.getUserId())
            .username(credential.getUsername())
            .password(encryptedPassword)
            .build());
  }

  public void updateCredential(Credential credential) {

    val randomEncodingKey = encryptionService.getRandomEncodingKey();
    credential.setKey(randomEncodingKey);
    val encryptedPassword =
        encryptionService.encryptValue(credential.getPassword(), randomEncodingKey);
    credential.setPassword(encryptedPassword);

    credentialsMapper.update(credential);
  }

  public void deleteCredentials(String credentialId) {
    credentialsMapper.delete(credentialId);
  }
}
