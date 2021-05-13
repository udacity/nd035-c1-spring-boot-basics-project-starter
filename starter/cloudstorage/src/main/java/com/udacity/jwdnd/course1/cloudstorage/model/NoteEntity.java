package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class NoteEntity {
    private int noteId;
    private final String noteTitle;
    private final String noteDescription;
    private final int userid;
}
