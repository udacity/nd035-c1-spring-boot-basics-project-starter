package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteService {

  private final NoteMapper noteMapper;

  public Integer createNote(Note note) {

    return noteMapper.create(note);
  }

  public List<Note> readNotes(Integer userId) {
    return noteMapper.findNotesByUserId(userId);
  }
}
