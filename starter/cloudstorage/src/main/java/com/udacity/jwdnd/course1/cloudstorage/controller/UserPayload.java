package com.udacity.jwdnd.course1.cloudstorage.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPayload {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
