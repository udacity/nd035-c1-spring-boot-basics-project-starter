package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class NoteService {

    private final NoteMapper noteMapper;

    @Autowired
    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void addNote(Note note) {
        Note found = noteMapper.getNoteByUserIdAndTitle(note.getUserid(), note.getNotetitle());
        if (found != null) {
            throw new IllegalArgumentException("A note with the same name already exists for this user.");
        } else {
            noteMapper.insertNote(note);
        }
    }

    public void updateNote(Note note) {
        Note found = noteMapper.getNoteByUserIdAndTitle(note.getUserid(), note.getNotetitle());
        if (found == null) {
            throw new IllegalArgumentException("This note does not exist.");
        } else {
            noteMapper.updateNote(note);
        }
    }

    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }
}
