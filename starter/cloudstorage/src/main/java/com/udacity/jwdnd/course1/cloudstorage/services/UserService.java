package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public User registerNewUser(User user) {//TODO don't allow duplicate username - throw exception or something
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

        userMapper.createUser(User.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(hashedPassword)
                .salt(encodedSalt)
                .build());

        return userMapper.getUser(user.getUsername());
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username)==null;
    }

    public Integer getLoggedInUserId(Authentication authentication){
        return userMapper.getUser(authentication.getName()).getUserId();
    }
}
