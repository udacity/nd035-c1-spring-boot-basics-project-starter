package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid=#{userid}")
    ArrayList<File> getAllFiles(User user);

    @Select("SELECT COUNT(*) FROM FILES WHERE userid=#{userid} AND filename=#{filename}")
    int getCountByFilename(File file);

    @Select("SELECT COUNT(*) FROM FILES WHERE userid=#{userid}")
    Integer getFilesCount(User user);

    @Select("SELECT * FROM FILES WHERE fileid=#{fileId} AND userid=#{userid}")
    File getFileById(Integer userid,Integer fileId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int upload(File file);

    @Delete("DELETE FILES WHERE fileId=#{fileId} AND userid=#{userid}")
    int deleteFile(Integer userid, Integer fileId);
}