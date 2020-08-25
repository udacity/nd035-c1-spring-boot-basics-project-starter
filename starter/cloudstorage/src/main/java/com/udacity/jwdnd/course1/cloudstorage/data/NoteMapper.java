package com.udacity.jwdnd.course1.cloudstorage.data;

import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {

    final String getById = "SELECT * FROM NOTES WHERE userid = #{userId}";
    final String deleteById = "DELETE from NOTES WHERE noteid = #{noteId}";
    final String insert = "INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle},#{noteDescription},#{userId})" ;
    final String update = "UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}";

    @Select(getById)
    Note select(Integer noteId);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insert(Note note);

    @Delete(deleteById)
    void delete(Integer noteId);

    @Update(update)
    void update(Note note);




}
