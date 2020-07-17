package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class HomeService {

    FilesMapper filesMapper;
    NotesMapper notesMapper;
    CredentialsMapper credentialsMapper;
    EncryptionService encryptionService;

    public HomeService(FilesMapper filesMapper, NotesMapper notesMapper, CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.filesMapper = filesMapper;
        this.notesMapper = notesMapper;
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
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
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair pair = keyPairGenerator.generateKeyPair();
            newCredential.setKey(pair.getPublic().getFormat());
            System.out.println("The new key is: " + newCredential.getKey());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            newCredential.setKey("@()*&VKJH@#");
        }
        encryptionService.encryptValue(credentialForm.getPassword(), newCredential.getKey());
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
        System.out.println(">>> getAllFiles(" + userid + ")");
        return filesMapper.getAllFilesByUserId(userid);
    }

    public int deleteFile(int fileid, int userid) {
        return filesMapper.deleteFiles(fileid, userid);
    }

    public Files findFile(int userid, int fileid) {
        return filesMapper.findFileByFileId(userid, fileid);
    }

    public boolean doesFileExist(int userId, String fileName) {
        int doesExist = filesMapper.doesFileExist(userId, fileName);
        if (doesExist == 1) return true;
        return false;
    }
}
