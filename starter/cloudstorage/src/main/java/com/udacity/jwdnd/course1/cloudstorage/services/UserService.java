package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.security.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    @Autowired
    private HashService hashService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDetailService userDetailService;

    public UserService() {
    }

    public int createUser(User user) {
        String encodedSalt = userDetailService.generateRandomKey();
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.insertUser(user.getUsername(), encodedSalt, hashedPassword, user.getFirstname(), user.getLastname());
    }
}
