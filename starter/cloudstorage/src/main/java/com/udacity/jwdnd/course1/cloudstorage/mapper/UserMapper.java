package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * from users where username = #{username}")
    public User getUser(String username);

    @Insert("INSERT into users (username, salt, password, firstname, lastname) " +
            "values (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = false, keyProperty = "#{userid}")
    public Integer insert(User user);

    // TODO - need @Delete

    // TODO - need @Update
}
