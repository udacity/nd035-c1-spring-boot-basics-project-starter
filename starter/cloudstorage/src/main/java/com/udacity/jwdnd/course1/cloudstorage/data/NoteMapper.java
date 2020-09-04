package com.udacity.jwdnd.course1.cloudstorage.data;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    final String getByUserId = "SELECT * FROM NOTES WHERE userid = #{userId}";
    final String getByNoteId = "SELECT * FROM NOTES WHERE noteid = #{noteId}";
    final String deleteById = "DELETE from NOTES WHERE noteid = #{noteId}";
    final String insert = "INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle},#{noteDescription},#{userId})" ;
    final String update = "UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}";

    @Select(getByUserId)
    List<Note> select(Integer userId);

    @Select(getByNoteId)
    Note getNote(Integer noteId);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insert(Note note);

    @Delete(deleteById)
    void delete(Integer noteId);

    @Update(update)
    void update(Note note);




}
