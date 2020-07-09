package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM User WHERE userId = #{userId}")
    User selectUser(Integer userId);

    @Insert("INSERT INTO User(userName, salt, password, firstName,lastName) VALUES (#{userName}, " +
            "${salt}," +
            " #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    Integer insertUser(User user);

    @Delete("DELETE FROM User WHERE userId = #{userId}")
    void deleteUser(Integer userId);
}
