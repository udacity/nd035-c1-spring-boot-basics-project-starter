package safwat.cloudstorage.services;

import safwat.cloudstorage.model.File;
import safwat.cloudstorage.model.User;

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
	
	 public List<File> getAllFiles(User user){
		return fileMapper.findAllFile(user);
	 }
	 
	 
	 public void deleteFile(int fileId) {
		 fileMapper.delete(fileId);
	 }
	 
	 public boolean isFileNameAvailable(String fileName, int userId){
		 
		 if(fileMapper.findFileByName(fileName) != null) {
			 File foundedFileName = fileMapper.findFileByName(fileName);
			 int id = foundedFileName.getUserId();
			 String name = foundedFileName.getFileName();
			 
			 if(name.equals(fileName) && id == userId ) {
				 return false;
			 }
		 }
		 
		 
		 return true;
	 }
}
