package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.controller.UserPayload;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final HashService hashService;
    private final UserMapper userMapper;

    protected UserService(HashService hashService,
                          UserMapper userMapper) {
        this.hashService = hashService;
        this.userMapper = userMapper;
    }

    public int createUser(UserPayload userPayload) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(userPayload.getPassword(), encodedSalt);
        return userMapper.insertUser(
            new User(
                userPayload.getUsername(),
                encodedSalt,
                hashedPassword,
                userPayload.getFirstName(),
                userPayload.getLastName()
            )
        );
    }

    public boolean isUsernameAvailable(String username) {
        return this.userMapper.getUser(username) == null;
    }

    public int getUserId(String username) {
        User user = this.userMapper.getUser(username);
        return user == null ? -1 : user.getUserId();
    }

    public int deleteUser(String username) {
        return this.userMapper.deleteUser(username);
    }
}
