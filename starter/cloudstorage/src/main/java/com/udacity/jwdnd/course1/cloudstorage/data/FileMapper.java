package com.udacity.jwdnd.course1.cloudstorage.data;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface FileMapper {

    final String getByUserId = "SELECT * FROM FILES WHERE userid = #{userId}";
    final String deleteByFileId = "DELETE from FILES WHERE fileId = #{fileId}";
    final String insert = "INSERT INTO FILES (filename, contenttype, filesize, userid, filedata, ) VALUES(#{fileName},#{contentType},#{fileSize}, #{userId}, #{fileData})" ;

    @Select(getByUserId)
    File select(Integer userId);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insert(File file);

    @Delete(deleteByFileId)
    void delete(Integer fileId);


}
