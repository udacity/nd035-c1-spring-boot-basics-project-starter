package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class File {
  private Integer fileId;
  private String fileName;
  private String contentType;
  private long fileSize;
  private Integer userId;
  private byte[] fileData;
}
