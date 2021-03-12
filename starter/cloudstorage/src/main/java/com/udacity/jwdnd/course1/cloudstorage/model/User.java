package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class User {
  private Integer userId;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private String salt;
}
