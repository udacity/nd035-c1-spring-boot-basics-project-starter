package safwat.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

import safwat.cloudstorage.mappers.UserMapper;
import safwat.cloudstorage.model.User;

@Service
public class UserService {
	
	UserMapper userMapper;
	HashService hashService;
	
	public UserService(UserMapper userMapper, HashService hashService) {
		// TODO Auto-generated constructor stub
		this.userMapper = userMapper;
		this.hashService = hashService;
	}
	
	
	public boolean isUserAvailable(User user) {
		if(userMapper.findUserByUserName(user.getUserName()) == null) {
			return true;
		}
		return false;
	}
	
	public int createUser(User user) {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		
		String encodedSalt = Base64.getEncoder().encodeToString(salt);
		
		String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
		
		
		return userMapper.insert(new User(encodedSalt, user.getFirstName(), 
				user.getLastName(), user.getUserName(), hashedPassword));
	}
}
