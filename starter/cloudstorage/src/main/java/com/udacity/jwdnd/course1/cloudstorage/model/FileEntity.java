package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class FileEntity {
    private int fileId;
    private final String filename;
    private final String contentType;
    private final String fileSize;
    private final int userid;
    private final byte[] fileData;
}
