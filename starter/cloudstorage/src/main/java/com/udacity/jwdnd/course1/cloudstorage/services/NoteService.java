package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Integer addNote(Integer userId, NoteForm noteForm) {
        System.out.println("Trying to add new note...");
        Note newNote = new Note();
        newNote.setNoteTitle(noteForm.getNoteTitle());
        newNote.setNoteDescription(noteForm.getNoteDescription());
        newNote.setUserId(userId);
        System.out.println("Adding new note to database with title: " + newNote.getNoteTitle());

        return noteMapper.insert(newNote);
    }

    public List<Note> getNotesList(Integer userid) {
        System.out.println("Fetching notes list from database with size: " + noteMapper.getAllNotesForUser(userid).size());
        return noteMapper.getAllNotesForUser(userid);
    }


}
