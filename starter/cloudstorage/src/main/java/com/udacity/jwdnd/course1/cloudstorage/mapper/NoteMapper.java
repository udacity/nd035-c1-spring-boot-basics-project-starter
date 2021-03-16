package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import java.util.List;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {
  @Insert(
      "insert into NOTES (notetitle, notedescription, userid) values (#{noteTitle}, #{noteDescription}, #{userId})")
  @Options(useGeneratedKeys = true, keyProperty = "noteId")
  Integer create(Note note);

  @Select("select * from NOTES where userid = #{userId}")
  List<Note> findByUserId(Integer userId);

  @Update({
    "update NOTES set noteTitle = #{noteTitle}, noteDescription = #{noteDescription} where noteid = #{noteId}"
  })
  Integer update(Note note);

  @Delete("delete from NOTES where noteid = #{noteId}")
  Integer delete(String noteId);
}
