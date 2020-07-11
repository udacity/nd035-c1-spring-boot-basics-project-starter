package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Insert("INSERT into CREDENTIALS (username, key, password, userid) " +
            "values (#{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = false, keyProperty = "#{credentialid}")
    public Integer insertCredentials(Credentials credentials);

    @Select("SELECT (credentialid, username, key, password, userid) from CREDENTIALS")
    public List<Credentials> getAllCredentials();

    @Update("UPDATE CREDENTIALS set username = #{username}, key = #{key}, " +
            "password = #{password}, userid = #{userid}" +
            "where credentialid = #{credentialId}")
    public void updateCredential(int credentialId);
}
