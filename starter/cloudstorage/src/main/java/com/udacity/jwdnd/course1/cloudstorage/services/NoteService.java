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

    public void insert(Note note) {
        notesMapper.insert(note);
    }

    public Note createNote(String title, String description, String userName) {
        Note note =  new Note();
        note.setNoteTitle(title);
        note.setNoteDescription(description);

        return note;
    }

    public void deleteNote(int noteId) {
        notesMapper.deleteNote(noteId);
    }
}
