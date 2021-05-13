package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class User {
    private int userId;
    private final String username;
    private final String salt;
    private final String password;
    private final String firstName;
    private final String lastName;
}