package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Credential {
  Integer credentialId;
  String url;
  String username;
  String key;
  String password;
  String decryptedPassword;
  Integer userId;
}
