package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
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
        int valueReturned = notesMapper.insertNotes(newNote);
        System.out.println("addNote - valueReturned: " + valueReturned);
        return valueReturned;
    }

    public int updateNote(HomeForm homeForm) {
        return notesMapper.updateNotes(homeForm.getNoteId(), homeForm.getUserId(),
                homeForm.getNoteTitle(), homeForm.getNoteDescription());
    }

    public int deleteNote(int noteId, int userid) {
        return notesMapper.deleteNotes(noteId, userid);
    }

    public List<Notes> getAllNotes(int userId) {
        return notesMapper.findAllNotesByUserId(userId);
    }

    public int addCredentials(CredentialForm credentialForm) {
        return credentialsMapper.insertCredentials(createCredentials(credentialForm));
    }

    private Credentials createCredentials(CredentialForm credentialForm) {
        Credentials newCredential = new Credentials();
        if (credentialForm.getCredentialId() != 0)
            newCredential.setCredentialid(credentialForm.getCredentialId());
        newCredential.setKey(credentialForm.getKey());
        newCredential.setPassword(credentialForm.getPassword());
        newCredential.setUrl(credentialForm.getUrl());
        newCredential.setUsername(credentialForm.getUsername());
        newCredential.setUserid(credentialForm.getUserId());
        return newCredential;
    }

    public int updateCredential(CredentialForm credentialForm) {
        return credentialsMapper.updateCredential(createCredentials(credentialForm));
    }

    public int deleteCredential(int credentialid, int userid) {
        return credentialsMapper.deleteCredential(credentialid, userid);
    }

    public List<Credentials> getAllCredentials(int userid) {
        return credentialsMapper.getAllCredentialsByUserId(userid);
    }

    public int addFiles(Files files) {
        System.out.println(">>> addFiles");
        int fileid = filesMapper.insertFiles(files);
        System.out.println("addFiles - id returned: " + fileid);
        System.out.println("<<< addFiles");
        return fileid;
    }

    public List<Files> getAllFiles(int userid) {
        return filesMapper.getAllFilesByUserId(userid);
    }
}
