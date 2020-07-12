package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.HomeForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    FilesMapper filesMapper;
    NotesMapper notesMapper;
    CredentialsMapper credentialsMapper;

    public HomeService(FilesMapper filesMapper, NotesMapper notesMapper, CredentialsMapper credentialsMapper) {
        this.filesMapper = filesMapper;
        this.notesMapper = notesMapper;
        this.credentialsMapper = credentialsMapper;
    }

    public int addNote(HomeForm homeForm) {
        Notes newNote = new Notes(homeForm.getNoteTitle(), homeForm.getNoteDescription(), homeForm.getUserId());
        Integer temp = notesMapper.insertNotes(newNote);
        System.out.println("Inside addNote: ID returned: " + temp);
        return temp;
    }

    public void updateNote(HomeForm homeForm) {
        notesMapper.updateNotes(homeForm.getNoteId(), homeForm.getNoteTitle(), homeForm.getNoteDescription());
    }

    public List<Notes> getAllNotes() {
        return notesMapper.findAllNotes();
    }
}
