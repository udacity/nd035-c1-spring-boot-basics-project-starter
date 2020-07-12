package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Insert("INSERT into notes (notetitle, notedescription, userid) " +
            "values (#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = false, keyProperty = "#{noteid}")
    public Integer insertNotes(Notes notes);

    // TODO - should only be for the given UserId!
    @Select("SELECT * FROM notes")
    public List<Notes> findAllNotes();

    //TODO - what does an update return, a 1 or a boolean?
    @Update("UPDATE notes set notetitle = #{notetitle}, notedescription = #{notedescription} " +
            "where noteid = #{noteid} ")
    public void updateNotes(int noteid, String notetitle, String notedescription);

    @Delete("DELETE notes where noteid = #{noteid}")
    public void deleteNotes(int noteid);
}
