package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM Note WHERE noteId = #{noteId}")
    Note selectNote(Integer noteId);

    @Insert("INSERT INTO Note(noteTitle, noteDescription, userId) VALUES (#{noteTitle}, " +
            "${noteDescription}," +
            " #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insertNote(Note note);

    @Delete("DELETE FROM Note WHERE noteId = #{noteId}")
    void deleteNote(Integer noteId);
}
