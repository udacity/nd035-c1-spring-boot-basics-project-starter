package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.controller.NotePayload;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteEntity;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public boolean insertNote(int userId, NotePayload notePayload) {
        return this.noteMapper.insertNote(new NoteEntity(
            notePayload.getNoteTitle(),
            notePayload.getNoteDescription(),
            userId
        )) > 0;
    }

    public boolean updateNote(int userId, NotePayload notePayload) {
        return this.noteMapper.updateNote(new NoteEntity(
            Integer.parseInt(notePayload.getNoteId()),
            notePayload.getNoteTitle(),
            notePayload.getNoteDescription(),
            userId
        )) > 0;
    }

    public List<NotePayload> getNotes(int userid) {
        return this.noteMapper.getNotesByUserId(userid)
            .stream()
            .map(NotePayload::fromEntity)
            .collect(Collectors.toList());
    }

    public boolean deleteNote(int userId, int noteId) {
        return this.noteMapper.deleteNoteByUserIdAndFileId(userId, noteId) > 0;
    }
}
