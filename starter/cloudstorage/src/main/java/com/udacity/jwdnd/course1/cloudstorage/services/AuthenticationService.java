package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import java.util.Collections;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationProvider {

  private final HashService hashService;
  private final UserMapper userMapper;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    val user =
        User.builder()
            .username(authentication.getName())
            .password(authentication.getCredentials().toString())
            .build();
    val isValidCredentials = validateCredentials(user);
    if (isValidCredentials) {
      return new UsernamePasswordAuthenticationToken(
          user.getUsername(), user.getPassword(), Collections.emptyList());
    }
    return null;
  }

  @Override
  public boolean supports(Class<?> auth) {
    return auth.equals(UsernamePasswordAuthenticationToken.class);
  }

  private boolean validateCredentials(User user) {
    val credentials = userMapper.findByUsername(user.getUsername());
    if (Objects.isNull(credentials)) {
      return false;
    }
    val hashedPassword = hashService.getHashedValue(user.getPassword(), credentials.getSalt());
    return credentials.getPassword().equals(hashedPassword);
  }
}
