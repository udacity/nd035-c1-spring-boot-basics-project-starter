package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService( NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public boolean submit(Note note){
        if (this.noteMapper.getNotesByTile(note)>0){
            System.out.println("Note already exists, try edit the existing or submit a new one!");
            return false;
        } else {
            return this.noteMapper.submit(note)==1;
        }

    }

    public Note create(User user, Note note){
        note = new Note(null, note.getNotetitle(), note.getNotedescription(), user.getUserid());
        return note;
    }

    public Boolean update(Note note){
        return this.noteMapper.update(note)==1;
    }

    public ArrayList<Note> getNotes(User user){
        return this.noteMapper.getAllNotes(user);
    }

    public Boolean deleteNote(Integer currentUser, Integer noteId) {
        return this.noteMapper.delete(currentUser,noteId)==1;
    }

    public Note getNote(Integer currentUser, Integer noteId) {
        return this.noteMapper.getNoteById(currentUser, noteId);
    }
}
