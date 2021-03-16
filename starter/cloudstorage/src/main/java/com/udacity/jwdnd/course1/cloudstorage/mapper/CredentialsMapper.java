package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Select("select * from CREDENTIALS where userid = #{userId}")
    List<Credential> findByUserid(Integer Id);

    @Insert("insert into CREDENTIALS (url, username, key, password, userid) values (#{url}, #{username}, #{key}, #{password}, #{userId})")
    Integer create(Credential credential);
}
