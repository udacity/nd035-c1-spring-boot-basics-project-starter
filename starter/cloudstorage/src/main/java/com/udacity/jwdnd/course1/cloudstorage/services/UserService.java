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

  public Integer createUser(User user) throws Exception {

    if (Objects.nonNull(userMapper.findUserByUsername(user.getUsername()))) {
      throw new Exception("User already taken!");
    }

    return userMapper.create(user);
  }

  public boolean validateCredentials(User user) {
    val credentials = userMapper.findUserByUsername(user.getUsername());
    if (Objects.isNull(credentials)) {
      return false;
    }
    val hashedPassword = hashService.getHashedValue(user.getPassword(), credentials.getSalt());
    return credentials.getPassword().equals(hashedPassword);
  }
}
