package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Model.SignupForm;
import com.udacity.jwdnd.course1.cloudstorage.Mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;

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


    public boolean isUserNameAvailable(String userName) {
        return userMapper.getUser(userName) == null;
    }

    public int createUser(SignupForm signupForm) {
        String[] result = encodePassword(signupForm.getPassword());
        return userMapper.insert(new User(null, signupForm.getUserName(), result[1], result[0], signupForm.getFirstName(), signupForm.getLastName()));
    }

    private String[] encodePassword(String password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(password, encodedSalt);
        return new String[]{hashedPassword, encodedSalt};
    }

    public User getUser(String username) {
        return userMapper.getUser(username);
    }

}
