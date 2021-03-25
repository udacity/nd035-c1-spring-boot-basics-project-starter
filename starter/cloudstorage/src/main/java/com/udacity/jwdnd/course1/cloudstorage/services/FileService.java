package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

  private final UserMapper userMapper;
  private final FileMapper fileMapper;

  public Integer createFileForUser(File file, String username) {
    val user = userMapper.findByUsername(username);

    file.setUserId(user.getUserId());

    return fileMapper.create(file);
  }

  public List<File> findFilesByUsername(String username) {
    val user = userMapper.findByUsername(username);
    return fileMapper.findByUserId(user.getUserId());
  }

  public Integer deleteFile(String fileId) {
    return fileMapper.delete(fileId);
  }

  public File getFile(String fileId) {
    return fileMapper.findById(fileId);
  }

  public boolean isFileNameAvailable(String originalFilename, String username) {
    val user = userMapper.findByUsername(username);
    val filesWithName = fileMapper.isFileNameAvailable(originalFilename, user.getUserId());
    return filesWithName <= 0;
  }
}
