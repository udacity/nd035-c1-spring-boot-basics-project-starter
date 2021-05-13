package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotePayload {
    private String noteId;
    private String noteTitle;
    private String noteDescription;

    static public NotePayload fromEntity(NoteEntity entity) {
        return new NotePayload(
            String.valueOf(entity.getNoteId()),
            entity.getNoteTitle(),
            entity.getNoteDescription()
        );
    }
}
