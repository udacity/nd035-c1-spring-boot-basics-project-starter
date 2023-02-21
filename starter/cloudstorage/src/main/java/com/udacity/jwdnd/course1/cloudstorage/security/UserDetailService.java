package com.udacity.jwdnd.course1.cloudstorage.security;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserDetailService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HashService hashService;

    public Integer getCurrentUserId() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userMapper.getUserByUsername(currentUsername);
        return currentUser.getUserId();
    }

    public String generateRandomKey() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
