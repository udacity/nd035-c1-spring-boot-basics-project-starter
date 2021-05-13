package com.udacity.jwdnd.course1.cloudstorage.model;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<CredentialEntity> getCredentialsByUserid(int userid);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid} AND credentialid = #{credentialId}")
    CredentialEntity getCredentialByUseridAndCredentialId(int userid, int credentialId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(CredentialEntity credentialEntity);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} WHERE credentialid = #{credentialId} AND userid = #{userid}")
    int updateCredential(CredentialEntity credentialEntity);

    @Delete("DELETE CREDENTIALS WHERE userid = #{userId} AND credentialid = #{credentialId}")
    int deleteCredentialByUserIdAndCredentialId(int userId, int credentialId);
}
