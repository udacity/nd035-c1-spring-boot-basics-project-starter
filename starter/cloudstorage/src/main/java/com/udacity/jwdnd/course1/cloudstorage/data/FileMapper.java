package com.udacity.jwdnd.course1.cloudstorage.data;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    final String getByUserId = "SELECT * FROM FILES WHERE userid = #{userId}";
    final String getByFileId = "SELECT * FROM FILES WHERE fileId = #{fileId}";
    final String deleteByFileId = "DELETE from FILES WHERE fileId = #{fileId}";
    final String insert = "INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName},#{contentType},#{fileSize}, #{userId}, #{fileData})" ;

    @Select(getByUserId)
    List<File> select(Integer userId);

    @Select(getByFileId)
    File getFileById(Integer fileId);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insert(File file);

    @Delete(deleteByFileId)
    void delete(Integer fileId);


}
