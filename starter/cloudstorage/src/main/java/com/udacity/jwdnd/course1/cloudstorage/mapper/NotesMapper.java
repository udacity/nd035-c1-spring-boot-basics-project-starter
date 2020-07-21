package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Insert("INSERT into notes (notetitle, notedescription, userid) " +
            "values (#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid", keyColumn = "noteid")
    public Integer insertNotes(Notes notes);

    @Select("SELECT * FROM notes where userid = #{userid}")
    public List<Notes> findAllNotesByUserId(int userid);

    @Update("UPDATE notes set notetitle = #{notetitle}, notedescription = #{notedescription} " +
            "where noteid = #{noteid} and userid = #{userid}")
    public int updateNotes(int noteid, int userid, String notetitle, String notedescription);

    @Delete("DELETE notes where noteid = #{noteid} and userid = #{userid}")
    public int deleteNotes(int noteid, int userid);
}
