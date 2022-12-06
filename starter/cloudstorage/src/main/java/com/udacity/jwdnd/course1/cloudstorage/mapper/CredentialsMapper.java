package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Select("SELECT * FROM credentials WHERE userid = #{userId}")
    List<Credential> getCredentials(int userId);

    @Insert("INSERT INTO credentials (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Update("UPDATE credentials SET url = #{url}, username = #{username}, key = #{key}, password= #{password}, userid = #{userid} WHERE credentialid = #{credentialId}")
    void updateCredential(Credential credential);

    @Delete("DELETE FROM credentials WHERE credentialid = #{credentialId}")
    void deleteCredential(int credentialId);

    @Select("SELECT * FROM credentials WHERE credentialid = #{credentialId}")
    Credential getCredentialById(int credentialId);
}
