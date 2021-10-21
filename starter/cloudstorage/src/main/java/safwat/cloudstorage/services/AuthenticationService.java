package safwat.cloudstorage.services;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import safwat.cloudstorage.mappers.UserMapper;
import safwat.cloudstorage.model.User;

@Service
public class AuthenticationService implements AuthenticationProvider {
	
	
	
	UserMapper userMapper;
	HashService hashService;
	
	public AuthenticationService(UserMapper userMapper, HashService hashService) {
		// TODO Auto-generated constructor stub
		this.userMapper = userMapper;
		this.hashService = hashService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		String userName = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		User user = userMapper.findUserByUserName(userName);
		
		if(user != null) {
			String hashedPassword = hashService.getHashedValue(password, user.getSalt());
			if(hashedPassword.equals(user.getPassword())) {
				return new UsernamePasswordAuthenticationToken(userName, password, new ArrayList<>());
			}
		}
		
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
