package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * from Credentials where credentialId = #{credentialId}")
    Credential selectCredential(Integer credentialId);

    @Insert("INSERT INTO Credentials (url, username, key, password, userId) VALUES (#{url}, #{username}, #{key}, " +
            "#{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer insertCredentials(Credential credential);

    @Delete("DELETE FROM Credentials WHERE credentialId = #{credentialId}")
    void deleteCredential(Integer credentialId);
}
