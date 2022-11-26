package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Select("SELECT * FROM NOTES WHERE noteId=#{noteId}")
    Note getNote(Integer noteId);
    @Select("SELECT * FROM NOTES WHERE userId=#{userId}")
    List<Note> getNotesByUser(Integer userId);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription)"  + "VALUES(#{noteTitle}, #{noteDescription})")
    int insert(Note note);

    @Update("UPDATE NOTES SET notetitle=#{title}, notedescription=#{description}, WHERE noteid = #{noteId}")
    void updateNote(Integer noteId, String title, String description);

    @Delete("DELETE FROM NOTES WHERE noteId=#{noteId}")
    void deleteNote(int noteId);
}
