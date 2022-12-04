package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NotesMapper notesMapper;

    public NoteService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public List<Note> getNotesByUser(Integer userId) {
        List<Note> notes = notesMapper.getNotesByUser(userId);

        return notes;
    }

    public Note getNote(Integer noteId) {
        return notesMapper.getNote(noteId);
    }

    public int insert(Note note) {
        return notesMapper.insert(note);
    }

    public Note createNote(String title, String description, int userId) {
        Note note =  new Note();
        note.setNoteTitle(title);
        note.setNoteDescription(description);
        note.setUserId(userId);

        int noteId = insert(note);
        note.setNoteId(noteId);
        return note;
    }

    public void updateNote(int noteId, String title, String description) {
        notesMapper.updateNote(noteId, title, description);
    }

    public void deleteNote(int noteId) {
        notesMapper.deleteNote(noteId);
    }
}
