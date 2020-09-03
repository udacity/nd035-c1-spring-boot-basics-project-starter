package com.udacity.jwdnd.course1.cloudstorage.data;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    final String getByUserId = "SELECT * FROM CREDENTIALS WHERE userid = #{userId}";
    final String getByCredentialId = "SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}";
    final String deleteById = "DELETE from CREDENTIALS WHERE credentialid = #{credentialId}";
    final String insert = "INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url},#{username},#{key}, #{password}, #{userId})" ;
    final String update = "UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password= #{password}, userid = #{userId} WHERE credentialid = #{credentialId}";

    @Select(getByUserId)
    List<Credential> getCredentials(Integer id);

    @Select(getByCredentialId)
    Credential getCredential(Integer id);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer insert(Credential credential);

    @Update(update)
    void update(Credential credential);

    @Delete(deleteById)
    void deleteCredential(Integer id);


}
