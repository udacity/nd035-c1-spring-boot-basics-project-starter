package com.udacity.jwdnd.course1.cloudstorage.model;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into USERS (username, salt, password, firstname, lastname) values(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(User user);

    @Select("select * from USERS where username = #{username}")
    User getUser(String username);

    @Delete("DELETE USERS WHERE username = #{username}")
    int deleteUser(String username);
}
