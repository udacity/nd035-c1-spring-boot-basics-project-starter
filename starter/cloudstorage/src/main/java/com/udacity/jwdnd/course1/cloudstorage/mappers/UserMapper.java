package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM Users WHERE username = #{username}")
    User getUser(String username);

    @Insert("INSERT INTO Users(username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, " +
            "#{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    Integer insertUser(User user);

    @Delete("DELETE FROM Users WHERE userId = #{userid}")
    void deleteUser(Integer userId);
}
