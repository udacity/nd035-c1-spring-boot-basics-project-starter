package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotesService {
    private final NotesMapper notesMapper;

    public NotesService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public void addNote(NoteForm noteForm, Integer userId) {
        Notes note = Notes.builder()
                .userId(userId)
                .noteId(noteForm.getNoteId())
                .noteTitle(noteForm.getNoteTitle())
                .noteDescription(noteForm.getNoteDescription())
                .build();
        if(noteForm.getNoteId()==null)
            notesMapper.saveNote(note);
        else notesMapper.updateNote(note);
    }

    public List<NoteForm> getNotes(Integer userId) {
        return notesMapper.getNotes(userId)
                .stream()
                .map(this::getNoteForm)
                .collect(Collectors.toList());
    }


    public void deleteNote(Integer noteId) {
        notesMapper.deleteNote(noteId);
    }

    private NoteForm getNoteForm(Notes note) {
        return NoteForm.builder()
                .noteId(note.getNoteId())
                .noteTitle(note.getNoteTitle())
                .noteDescription(note.getNoteDescription())
                .build();
    }
}
