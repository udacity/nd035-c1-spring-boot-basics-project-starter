package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getFilesByUserId(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId} AND userId = #{userId}")
    File getFilesByFileIdAndUserId(Integer fileId, Integer userId);

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, userId, fileData) VALUES" +
            "(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    int insertFile(String fileName, String contentType, String fileSize, Integer userId, byte[] fileData);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFile(Integer fileId);
}
