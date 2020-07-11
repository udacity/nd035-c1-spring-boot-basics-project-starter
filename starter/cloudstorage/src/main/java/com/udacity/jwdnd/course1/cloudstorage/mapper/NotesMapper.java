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

    @Select("SELECT * FROM notes")
    public List<Notes> findAllNotes();

    @Update("UPDATE notes set notetitle = #{notetitle}, notedescription = #{notedescription}," +
            "userid = #{userid} where noteid = #{noteid}")
    public void updateNotes(int noteid);

    @Delete("DELETE notes where noteid = #{noteid}")
    public void deleteNotes(int noteid);
}
