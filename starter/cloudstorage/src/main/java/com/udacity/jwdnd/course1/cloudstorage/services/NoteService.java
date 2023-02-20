package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.security.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private UserDetailService userDetailService;

    public List<Note> getNotesByCurrentUserId() {
        return noteMapper.getNotesByUserId(userDetailService.getCurrentUserId());
    }

    public int addNote(Note note) {
        return noteMapper.insertNote(note.getNoteTitle(),
                note.getNoteDescription(),
                userDetailService.getCurrentUserId());
    }

    public int updateNote(Note note) {
        return noteMapper.updateNote(note.getNoteId(), note.getNoteTitle(),
                note.getNoteDescription(),
                userDetailService.getCurrentUserId());
    }
}
