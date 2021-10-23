package safwat.cloudstorage.services;

import safwat.cloudstorage.model.File;

import java.util.List;

import org.springframework.stereotype.Service;

import safwat.cloudstorage.mappers.FileMapper;

@Service
public class FileService {
	
	 FileMapper fileMapper;
	 public FileService(FileMapper fileMapper) {
		 this.fileMapper = fileMapper;
	 }
	 
	 
	 public File getFileById(int id) {
		 return fileMapper.findFileById(id);
	 }
	
	 public void saveFile(File file) {
		fileMapper.insertFile(file);
	 }
	
	 public List<File> getAllFiles(){
		return fileMapper.findAllFile();
	 }
	 
	 
	 public void deleteFile(int fileId) {
		 fileMapper.delete(fileId);
	 }
}
