package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid=#{userid}")
    ArrayList<Note> getAllNotes(User user);

    @Select("SELECT * FROM NOTES WHERE userid=#{userid} and noteid=${noteid}")
    Note getNoteById(Integer userid, Integer noteid);

    @Select("SELECT COUNT(*) FROM NOTES WHERE notetitle=#{notetitle}")
    Integer getNotesByTile(Note note);

    @Insert("INSERT INTO NOTES (notetitle, notedescription,userid) VALUES (#{notetitle},#{notedescription},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int submit(Note note);

    @Delete("DELETE FROM NOTES WHERE userid=#{userid} AND noteid=#{noteid}")
    int delete(Integer userid, Integer noteid);

    @Update("UPDATE NOTES SET notetitle=#{notetitle}, notedescription=#{notedescription} WHERE noteid=#{noteid} and userid=#{userid}")
    int update(Note note);

}
