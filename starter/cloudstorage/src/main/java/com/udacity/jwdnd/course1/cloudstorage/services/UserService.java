package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final HashService hashService;

    @Autowired
    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public void addUser(User user) {
        User existingUser = userMapper.selectUserByUsername(user.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("Username already exists. Please choose a different username.");
        }
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        if (hashedPassword.length() > 255) {
            throw new IllegalArgumentException("Hashed password exceeds the maximum allowed length of 255 characters.");
        }
        user.setSalt(encodedSalt);
        user.setPassword(hashedPassword);
        userMapper.insertUser(user);
    }

    public void loginUser(User user) {

    }

    public void getUser(User user) {
        userMapper.selectUserByUsername(user.getUsername());
    }
}
