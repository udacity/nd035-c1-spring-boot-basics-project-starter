package com.udacity.jwdnd.course1.cloudstorage.model;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<NoteEntity> getNotesByUserId(int userId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(NoteEntity noteEntity);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId} AND userid = #{userid}")
    int updateNote(NoteEntity noteEntity);

    @Delete("DELETE NOTES WHERE userid = #{userId} AND noteid = #{noteId}")
    int deleteNoteByUserIdAndFileId(int userId, int noteId);
}
