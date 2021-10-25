package safwat.cloudstorage.services;

import java.util.List;

import org.springframework.stereotype.Service;

import safwat.cloudstorage.mappers.NoteMapper;
import safwat.cloudstorage.model.Note;
import safwat.cloudstorage.model.User;


@Service
public class NoteService {
	
	
	public NoteService(NoteMapper noteMapper) {
		// TODO Auto-generated constructor stub
		this.noteMapper = noteMapper;
		//notesList = new ArrayList<Note>();
	}
	
	NoteMapper noteMapper;
	
	
	
	public int createNote(Note note) {
		return noteMapper.insertNote(note);
	}
	
	public Note getNoteById(int id) {
		return noteMapper.findNoteById(id);
	}
	
	public int updateNote(Note note) {
		return noteMapper.updateNote(note);
	}
	
	public void deleteNote(int noteId) {
		noteMapper.deleteNote(noteId);
	}
	
	public List<Note> getAllNotes(User user){
		return noteMapper.findAllNotes(user);
	}
}
