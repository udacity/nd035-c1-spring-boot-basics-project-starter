package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import java.util.List;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialsMapper {

  @Select("select * from CREDENTIALS where userid = #{userId}")
  List<Credential> findByUserid(Integer Id);

  @Insert(
      "insert into CREDENTIALS (url, username, key, password, userid) values (#{url}, #{username}, #{key}, #{password}, #{userId})")
  Integer create(Credential credential);

  @Delete("delete from CREDENTIALS where credentialid = #{credentialId}")
  Integer delete(String credentialId);

  @Update(
      "update CREDENTIALS set url = #{url}, username = #{username}, password = #{password}, key = #{key} where credentialid = #{credentialId}")
  Integer update(Credential credential);
}
