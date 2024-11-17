package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE id = #{id}")
    User selectUserById(Long id);

    @Select("SELECT * FROM users WHERE username = #{username}")
    User selectUserByUsername(String username);

    @Insert("INSERT INTO users (username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    void deleteUserById(Long id);

    @Update("UPDATE users SET username = #{username}, email = #{email}, salt = #{salt}, password = #{password} WHERE id = #{id}")
    void updateUser(User user);

    @Select("SELECT * FROM users")
    List<User> selectAllUsers();
}
