package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NoteMapper {
  @Insert(
      "insert into NOTES (notetitle, notedescription) values (#{noteTitle}, #{noteDescription})")
  @Options(useGeneratedKeys = true, keyProperty = "noteId")
  Integer create(Note note);

  @Select("select * from NOTES where userid = #{userId}")
  List<Note> findNotesByUserId(Integer userId);
}
