package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM Notes WHERE noteId = #{noteId}")
    Note selectNote(Integer noteId);

    @Insert("INSERT INTO Notes(noteTitle, noteDescription, userId) VALUES (#{noteTitle}, " +
            "${noteDescription}," +
            " #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insertNote(Note note);

    @Delete("DELETE FROM Notes WHERE noteId = #{noteId}")
    void deleteNote(Integer noteId);
}
