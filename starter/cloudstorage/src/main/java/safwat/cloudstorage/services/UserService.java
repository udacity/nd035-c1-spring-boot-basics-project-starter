package safwat.cloudstorage.services;

import org.springframework.stereotype.Service;

import safwat.cloudstorage.mappers.UserMapper;
import safwat.cloudstorage.model.User;

@Service
public class UserService {
	
	UserMapper userMapper;
	
	public UserService(UserMapper userMapper) {
		// TODO Auto-generated constructor stub
		this.userMapper = userMapper;
	}
	
	
	public boolean isUserAvailable(User user) {
		if(userMapper.findUserByUserName(user.getUserName()) == null) {
			return true;
		}
		return false;
	}
	
	public void createUser(User user) {
		userMapper.insert(user);
	}
}
