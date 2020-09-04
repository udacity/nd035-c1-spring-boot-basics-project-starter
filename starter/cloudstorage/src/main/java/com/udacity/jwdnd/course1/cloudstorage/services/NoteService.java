package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private UserService userService;

    public List<Note> getNotes() {

        String username = UserService.getLoggedInUsername();
        User user = userService.getUser(username);

        if (user.getUserId() != null) {
            List<Note> notes = noteMapper.select(user.getUserId());
            return notes;
        }

        return null;

    }

    public Message addNote(NoteForm noteForm) {

        String username = UserService.getLoggedInUsername();
        User user = userService.getUser(username);

        Note newNote = new Note();

        if (user.getUserId() != null) {
            newNote.setNoteTitle(noteForm.getNoteTitle());
            newNote.setNoteDescription(noteForm.getNoteDescription());
            newNote.setUserId(user.getUserId());

            noteMapper.insert(newNote);
        }

        return new Message(false, true,"Note successfully added!");
    }

    public Message editNote(NoteForm noteForm) {

        String username = UserService.getLoggedInUsername();
        User user = userService.getUser(username);

        Note note = noteMapper.getNote(noteForm.getNoteId());

        if (note.getUserId().equals(user.getUserId())) {

            Note updateNote = new Note();

            updateNote.setNoteId(noteForm.getNoteId());
            updateNote.setNoteTitle(noteForm.getNoteTitle());
            updateNote.setNoteDescription(noteForm.getNoteDescription());
            updateNote.setUserId(user.getUserId());

            noteMapper.update(updateNote);

            return new Message(false, true,"Note successfully edited!");
        }
        return new Message(true, false,"Unable to edit note. Please make sure you have the right authorization");
    }

    public Message deleteNote(Integer id) {
        String username = UserService.getLoggedInUsername();
        User user = userService.getUser(username);

        Note note = noteMapper.getNote(id);

        if (note.getUserId().equals(user.getUserId())) {
            noteMapper.delete(id);

            return new Message(false, true,"Note successfully deleted!");
        }

        return new Message(true, false,"Unable to delete note. Please make sure you have the right authorization");
    }
}
