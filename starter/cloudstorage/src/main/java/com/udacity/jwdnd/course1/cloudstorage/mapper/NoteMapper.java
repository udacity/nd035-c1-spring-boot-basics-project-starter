package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNoteByNoteId(Integer noteId);

    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Note> getNotesByUserId(Integer userId);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES" +
            "(#{noteTitle}, #{noteDescription}, #{userId})")
    int insertNote(String noteTitle, String noteDescription, Integer userId);

    @Update("UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription = " +
            "#{noteDescription} WHERE noteId = #{noteId} and userId = " +
            "#{userId}")
    int updateNote(Integer noteId, String noteTitle, String noteDescription,
                   Integer userId);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    int deleteNote(Integer noteId);
}
