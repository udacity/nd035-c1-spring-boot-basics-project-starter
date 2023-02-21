package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credential> getCredentialsByUserId(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId) VALUES" +
            "(#{url}, #{username}, #{key}, #{password}, #{userId})")
    int insertCredential(String url, String username, String key, String password, Integer userId);
}
