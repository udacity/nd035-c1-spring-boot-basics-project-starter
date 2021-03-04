package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS (url,username,key,password,userid) VALUES (#{url},#{username},#{key},#{password},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int create(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userid}")
    ArrayList<Credential> getAll(User user);

    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userid} AND credentialid=#{credentialid}")
    Credential getById(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE userid=#{userid} AND credentialid=#{credentialid}")
    int delete(Integer userid, Integer credentialid);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username},key=#{key},password=#{password} WHERE credentialid=#{credentialid} AND userid=#{userid}" )
    int update(Credential credential);

}
