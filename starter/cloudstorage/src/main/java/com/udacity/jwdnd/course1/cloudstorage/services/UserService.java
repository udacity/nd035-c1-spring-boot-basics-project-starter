package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserMapper userMapper;
  private final HashService hashService;
  private final EncryptionService encryptionService;

  public void createUser(User user) throws Exception {
    val prospectUser = userMapper.findByUsername(user.getUsername());
    if (Objects.nonNull(prospectUser)) {
      throw new Exception("Username already taken!");
    }

    String encodedSalt = encryptionService.getRandomEncodingKey();
    String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

    user.setPassword(hashedPassword);
    user.setSalt(encodedSalt);

    userMapper.create(user);
  }
}
