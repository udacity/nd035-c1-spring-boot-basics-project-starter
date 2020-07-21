package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Insert("INSERT into CREDENTIALS (url, username, key, password, userid) " +
            "values (#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    public Integer insertCredentials(Credentials credentials);

    @Select("SELECT credentialid, url, username, key, password, userid from CREDENTIALS " +
            "where userid = #{userid}")
    public List<Credentials> getAllCredentialsByUserId(int userid);

    @Update("UPDATE CREDENTIALS set url = #{url}, username = #{username}, key = #{key}, " +
            "password = #{password}, userid = #{userid} " +
            "where credentialid = #{credentialid} and userid = #{userid}")
    public int updateCredential(Credentials credentials);

    @Delete("DELETE from CREDENTIALS where credentialid = #{credentialid} " +
            "and userid = #{userid}")
    public int deleteCredential(int credentialid, int userid);
}
