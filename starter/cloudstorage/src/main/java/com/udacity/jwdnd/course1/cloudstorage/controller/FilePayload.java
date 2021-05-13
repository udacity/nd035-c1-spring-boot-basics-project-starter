package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FilePayload {
    private final int fileId;
    private final String filename;

    static public FilePayload fromEntity(FileEntity entity) {
        return new FilePayload(entity.getFileId(), entity.getFilename());
    }
}
