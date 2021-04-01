package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

@Service
public class NotesService {
    private final NotesMapper notesMapper;

    public NotesService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public void addNote(NoteForm noteForm, Integer userId) {
        notesMapper.saveNote(Notes.builder()
                .userId(userId)
                .noteTitle(noteForm.getNoteTitle())
                .noteDescription(noteForm.getNoteDescription())
                .build());
    }

    public Object getNotes(Integer userId) {
        return notesMapper.getNotes(userId);
    }

    public void deleteNote(Integer noteId) {
        notesMapper.deleteNote(noteId);
    }
/*
    public Object getNotes(String username) {
        notesMapper.getNotes();

    }

 */
}
