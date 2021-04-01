package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
    @Insert("INSERT INTO Notes(notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    void saveNote(Notes note);

    @Select("SELECT * FROM Notes WHERE userid= #{userId}")
    List<Notes> getNotes(Integer userId);

    @Delete("DELETE FROM Notes where noteid=#{noteId}")
    void deleteNote(Integer noteId);
}
