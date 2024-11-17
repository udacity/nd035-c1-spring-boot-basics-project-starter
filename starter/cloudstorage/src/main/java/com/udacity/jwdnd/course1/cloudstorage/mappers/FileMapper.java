package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO files (filename, contenttype, filesize, userid, filedata) " +
            "VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void insertFile(File file);

    @Select("SELECT * FROM files WHERE fileId = #{fileId}")
    File getFileById(Integer fileId);

    @Select("SELECT * FROM files WHERE userid = #{userId}")
    List<File> getFilesByUserId(Integer userId);

    @Delete("DELETE FROM files WHERE fileId = #{fileId}")
    void deleteFileById(Integer fileId);
}