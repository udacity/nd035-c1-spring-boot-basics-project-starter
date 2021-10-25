package safwat.cloudstorage.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import safwat.cloudstorage.model.Note;
import safwat.cloudstorage.model.User;

@Mapper
public interface NoteMapper {
	
	
	@Select("SELECT * FROM NOTES WHERE notetitle = #{noteTitle}")
	Note findNoteByTitle(String noteTitle);

	@Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
	Note findNoteById(int noteId);

	
	@Select("SELECT * FROM NOTES WHERE userid = #{userId}")
	List<Note> findAllNotes(User user);
	
	@Insert("INSERT INTO NOTES (notetitle, notedescription, userid) "
			+ "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "noteId")
	int insertNote(Note note);
	
	@Update("UPDATE NOTES SET notetitle = #{noteTitle}, "
			+ "notedescription = #{noteDescription} WHERE noteid = #{noteId}")
	int updateNote(Note note);
	
	@Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
	void deleteNote(int noteId);
}
