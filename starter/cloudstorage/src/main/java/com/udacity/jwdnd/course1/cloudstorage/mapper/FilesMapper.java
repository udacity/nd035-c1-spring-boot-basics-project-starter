package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {
    @Insert("INSERT into FILES (filename, contenttype, filesize, userid, filedata) " +
            "values (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = false, keyProperty = "#{fileid}")
    public Integer insertFiles(Files files);

    @Select("SELECT fileid, filename, contenttype, filesize, userid, filedata from FILES " +
            "where userid = #{userid}")
    public List<Files> getAllFilesByUserId(int userid);

    @Update("UPDATE FILES set filename = #{filename}, contenttype = #{contenttype}, filesize = #{filesize}, userid = #{userid}, filedata = #{filedata} " +
            "where fileid = #{fileid} and userid = #{userid}")
    public int updateFile(Files files);

    @Delete("DELETE from FILES where fileid = #{fileid} " +
            "and userid = #{userid}")
    public int deleteFiles(int fileid, int userid);
}
