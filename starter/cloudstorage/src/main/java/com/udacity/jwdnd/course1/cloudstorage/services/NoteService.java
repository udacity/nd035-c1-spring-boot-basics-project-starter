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

    public void addNote(Integer userId, NoteForm noteForm) {
        Note newNote = new Note();
        newNote.setnotetitle(noteForm.getNoteTitle());
        newNote.setnotedescription(noteForm.getNoteDescription());
        newNote.setUserid(userId);

        noteMapper.insert(newNote);
    }

    public List<Note> getNotesList(Integer userid) {
        return noteMapper.getAllNotesForUser(userid);
    }


}
