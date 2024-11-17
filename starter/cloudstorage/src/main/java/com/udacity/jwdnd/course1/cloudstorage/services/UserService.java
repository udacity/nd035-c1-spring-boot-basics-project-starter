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

    private final EncryptionService encryptionService;

    @Autowired
    public UserService(UserMapper userMapper, HashService hashService, EncryptionService encryptionService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
        this.encryptionService = encryptionService;
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

    public void validateUser(User user) {
        User found = userMapper.selectUserByUsername(user.getUsername());
        if (found == null) {
            throw new IllegalArgumentException("User not found");
        }

        String storedSalt = user.getSalt();
        String storedHashedPassword = user.getPassword();

        String hashedInputPassword = hashService.getHashedValue(user.getPassword(), storedSalt);

        if (!hashedInputPassword.equals(storedHashedPassword)) {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }

    public void validateUser(String username, String password) {
        User user = userMapper.selectUserByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        String storedSalt = user.getSalt();
        String storedHashedPassword = user.getPassword();

        String hashedInputPassword = hashService.getHashedValue(password, storedSalt);

        if (!hashedInputPassword.equals(storedHashedPassword)) {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }
}
