package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM Files WHERE fileId = #{fileId}")
    File selectFile(Integer fileId);

    @Insert("INSERT INTO Files(fileName, contentType, fileSize, userId, fileData) VALUES (#{fileName}, " +
            "${contentType}," +
            " #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insertFile(File file);

    @Delete("DELETE FROM Files WHERE fileId = #{fileId}")
    void deleteFile(Integer fileId);
}
