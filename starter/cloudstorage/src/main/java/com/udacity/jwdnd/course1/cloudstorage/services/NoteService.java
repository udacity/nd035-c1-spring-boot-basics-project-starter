package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private UserService userService;

    public Message addNote(NoteForm noteForm) {

        String username = UserService.getLoggedInUsername();
        User user = userService.getUser(username);

        Note newNote = new Note();

        newNote.setNoteTitle(noteForm.getNoteTitle());
        newNote.setNoteDescription(noteForm.getNoteDescription());
        newNote.setUserId(user.getUserId());

        noteMapper.insert(newNote);

        return new Message(false, true,"Note successfully added!");
    }
}
