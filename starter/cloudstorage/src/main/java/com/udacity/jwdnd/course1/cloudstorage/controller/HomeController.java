package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.constants.Attributes;
import com.udacity.jwdnd.course1.cloudstorage.constants.Templates;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import java.io.IOException;
import java.security.Principal;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@ControllerAdvice
@RequiredArgsConstructor
public class HomeController {

  private final NoteService noteService;
  private final CredentialService credentialService;
  private final FileService fileService;

  @GetMapping("/home")
  public String getHome(Principal principal, Note note, Credential credential, Model model) {
    val username = principal.getName();

    prefillModel(model, username);

    return Templates.HOME;
  }

  @PostMapping("/credentials")
  public String postCredentialSet(
      Principal principal, Note note, Credential credential, Model model) {
    val username = principal.getName();

    if (Objects.isNull(credential.getCredentialId())) {
      credentialService.createCredentialsForUser(credential, username);
    } else {
      credentialService.updateCredential(credential);
    }

    prefillModel(model, username);

    return Templates.HOME;
  }

  @DeleteMapping("/credentials/{id}")
  public String deleteCredentialSet(
      Principal principal,
      @PathVariable("id") String credentialId,
      Note note,
      Credential credential,
      Model model) {
    val username = principal.getName();
    credentialService.deleteCredentials(credentialId);

    prefillModel(model, username);

    return Templates.HOME;
  }

  @PostMapping("/notes")
  public String postNote(Principal principal, Note note, Credential credential, Model model) {
    val username = principal.getName();

    if (Objects.isNull(note.getNoteId())) {
      noteService.createNoteForUser(note, username);
    } else {
      noteService.updateNote(note);
    }

    prefillModel(model, username);

    return Templates.HOME;
  }

  @DeleteMapping("/notes/{id}")
  public String deleteNote(
      Principal principal,
      @PathVariable("id") String noteId,
      Note note,
      Credential credential,
      Model model) {
    val username = principal.getName();
    val deletedNoteStatus = noteService.deleteNote(noteId);

    prefillModel(model, username);

    return Templates.HOME;
  }

  @PostMapping("/files")
  public String postFile(
      Principal principal,
      @RequestParam("fileUpload") MultipartFile fileUpload,
      Note note,
      Credential credential,
      Model model)
      throws IOException {
    val username = principal.getName();

    val fileUploadStatus =
        fileService.createFileForUser(
            File.builder()
                .fileName(fileUpload.getOriginalFilename())
                .contentType(fileUpload.getContentType())
                .fileSize(fileUpload.getSize())
                .fileData(fileUpload.getBytes())
                .build(),
            username);

    prefillModel(model, username);

    return Templates.HOME;
  }

  @DeleteMapping("/files/{id}")
  public String deleteFile(
          Principal principal,
          @PathVariable("id") String fileId,
          Note note,
          Credential credential,
          Model model) {
    val username = principal.getName();
    val deletedFileStatus = fileService.deleteFile(fileId);

    prefillModel(model, username);

    return Templates.HOME;
  }

  @GetMapping("/files/{id}")
  public ResponseEntity getFile(Principal principal, @PathVariable("id") String fileId, Note note, Credential credential, Model model) {
    val username = principal.getName();

    val file = fileService.getFile(fileId);

    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,  "attachment; filename=\"" + file.getFileName() + "\"").body(file.getFileData());

  }

  private void prefillModel(Model model, String username){
    model.addAttribute(Attributes.NOTES, noteService.findNotesByUsername(username));
    model.addAttribute(
            Attributes.CREDENTIALS, credentialService.findCredentialsByUsername(username));
    model.addAttribute(Attributes.FILES, fileService.findFilesByUsername(username));
  }
}