package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE fileId=#{fileId}")
    File getFile(Integer fileId);

    @Select("SELECT * FROM FILES WHERE fileName=#{fileName}")
    File getFileByName(String fileName);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata)" +
            "VALUES(#{filename}, #{contentType}, #{fileSize}, #{userId}, #{data})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Select("SELECT * FROM FILES WHERE userId=#{userId}")
    List<File> getAllFiles(int userId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteFile(int fileId);
}
