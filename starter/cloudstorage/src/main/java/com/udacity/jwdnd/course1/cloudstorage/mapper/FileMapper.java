package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileMapper {

  @Insert(
      "insert into FILES (filename, contenttype, filesize, userid, filedata) values (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
  Integer create(File file);

  @Select("select * from FILES where userid = #{userId}")
  List<File> findByUserId(Integer userId);

  @Delete("delete from FILES where fileid = #{fileId}")
  Integer delete(String fileId);

  @Select("select * from FILES where fileid = #{fileId}")
  File findById(String fileId);
}
