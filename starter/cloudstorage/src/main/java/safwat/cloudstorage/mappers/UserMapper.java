package safwat.cloudstorage.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import safwat.cloudstorage.model.User;

@Mapper
public interface UserMapper {
	
	@Select("SELECT * FROM USERS WHERE username = #{userName}")
	User findUserByUserName(String userName);
	
	
	@Insert("INSERT INTO USERS (username, salt, password, firstname, lastname)"
			+ " VALUES(#{userName}, #{salt}, #{password}, #{firstName}, #{lastName})")
	@Options(useGeneratedKeys = true, keyProperty = "userId")
	int insert(User user);
	
	@Delete("DELETE FROM USERS WHERE id=#{id}")
	void delete(int id);
}
