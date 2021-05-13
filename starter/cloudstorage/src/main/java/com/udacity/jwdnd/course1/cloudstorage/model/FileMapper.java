package com.udacity.jwdnd.course1.cloudstorage.model;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userid} AND filename = #{filename}")
    List<FileEntity> getFilesByUseridAndFilename(int userid, String filename);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<FileEntity> getFilesByUserid(int userid);

    @Select("SELECT * FROM FILES WHERE userid = #{userId} AND fileid = #{fileId}")
    FileEntity getFileByUserIdAndFileId(int userId, int fileId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contentType}, #{fileSize}, #{userid}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(FileEntity fileEntity);

    @Delete("DELETE FILES WHERE userid = #{userId} AND fileid = #{fileId}")
    int deleteFileByUserIdAndFileId(int userId, int fileId);
}
