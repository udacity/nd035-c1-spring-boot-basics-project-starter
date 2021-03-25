package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteService {

  private final NoteMapper noteMapper;
  private final UserMapper userMapper;

  public Integer createNoteForUser(Note note, String username) {
    val user = userMapper.findByUsername(username);
    note.setUserId(user.getUserId());
    return noteMapper.create(note);
  }

  public List<Note> findNotesByUsername(String username) {
    val user = userMapper.findByUsername(username);
    return noteMapper.findByUserId(user.getUserId());
  }

  public Integer deleteNote(String noteId) {
    return noteMapper.delete(noteId);
  }

  public Integer updateNote(Note note) {
    return noteMapper.update(note);
  }
}
