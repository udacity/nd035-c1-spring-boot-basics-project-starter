package safwat.cloudstorage.controllers;

import java.io.IOException;


import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.net.HttpHeaders;

import safwat.cloudstorage.model.Credentials;
import safwat.cloudstorage.model.File;
import safwat.cloudstorage.model.Note;
import safwat.cloudstorage.model.User;
import safwat.cloudstorage.services.CredentialsService;
import safwat.cloudstorage.services.NoteService;
import safwat.cloudstorage.services.UserService;
import safwat.cloudstorage.services.FileService;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	NoteService noteService;
	CredentialsService credentialsService;
	UserService userService;
	FileService fileService;
	
	public HomeController(NoteService noteService, CredentialsService credentialsService
			, UserService userService, FileService fileService) {
		// TODO Auto-generated constructor stub
		this.noteService = noteService;
		this.credentialsService = credentialsService;
		this.userService = userService;
		this.fileService = fileService;
	}
	
	
	@GetMapping
	public String getHomePage(Note note, Authentication auth, Model model) {
		
		
		User user = userService.getUserByUserName(auth.getName());
		model.addAttribute("notesList", noteService.getAllNotes(user));
		model.addAttribute("credentialsList", credentialsService.getAllCredentials(user));
		model.addAttribute("filesList", fileService.getAllFiles(user));
		
		model.addAttribute("credService", this.credentialsService);

		return "home";
	}
	
	
	@PostMapping
	public String postNotePage(Note note, Credentials credentials, Authentication auth, Model model) {
		
		
		User user = userService.getUserByUserName(auth.getName());
		
		if(note.getNoteDescription() != null || note.getNoteTitle() != null) {
			
			if(note.getNoteId() == null) {
				
				
				
				note.setUserId(user.getUserId());
				noteService.createNote(note);
				
			}
			else {
				noteService.updateNote(note);
			}
			
			model.addAttribute("saved", true);
		}
		
		if(credentials.getUrl() != null || credentials.getUserName() != null
				|| credentials.getPassword() != null) {
			
			
			if(credentials.getCredentialId() == null) {
				//User user = userService.getUserByUserName(auth.getName());

				credentials.setUserId(user.getUserId());
				
				credentialsService.createCredentials(credentials);
			}
			else {
				
				credentialsService.updateCredentials(credentials);
			}
			
			model.addAttribute("saved", true);
		}
//		if(credentials.getCredentialId() != null)
		model.addAttribute("credService", this.credentialsService);

		model.addAttribute("notesList", noteService.getAllNotes(user));
		model.addAttribute("credentialsList", credentialsService.getAllCredentials(user));
		
		return "result";
		
	}
	
	@GetMapping("/deleteNote/{noteid}")

	public String deleteNote(@PathVariable(value = "noteid") int noteId, Authentication authentication, Note note, Model model){
		
		
		noteService.deleteNote(noteId); 

		model.addAttribute("saved", true);
		
		return "result";

	}
	
	@GetMapping("/deleteCredential/{credentialId}")
	public String deleteCredential(@PathVariable(value = "credentialId") int credentialId, Model model) {
		credentialsService.deleteCredential(credentialId);
		
		return "redirect:/home";
	}
	
	@GetMapping("/deleteFile/{fileId}")
	public String deleteFile(@PathVariable(value = "fileId") int fileId, Model model) {
		
		fileService.deleteFile(fileId);
		
		model.addAttribute("saved", true);
		return "result";
	}
	
	
	@PostMapping("/files")
	public String uploadFiles(@RequestParam("fileUpload") MultipartFile fileUpload,Authentication auth, Model model ) throws IOException {
		
		User user = userService.getUserByUserName(auth.getName());

		File file = new File(null, null, null, null, null, null);
		file.setContentType(fileUpload.getContentType());
		file.setFileData(fileUpload.getBytes());
		file.setFileName(fileUpload.getOriginalFilename());
		file.setFileSize(fileUpload.getSize());
		file.setUserId(user.getUserId());
		
		if(fileService.isFileNameAvailable(file.getFileName(), file.getUserId())) {
			model.addAttribute("saved", true);
			fileService.saveFile(file);
		}
		
		else {
			model.addAttribute("error", true);
		}
		
		/*System.out.println(fileUpload.getContentType());
	    System.out.println(fileUpload.getBytes());
	    System.out.println(fileUpload.getOriginalFilename());
	    System.out.println(fileUpload.getSize());*/
	    
	    model.addAttribute("filesList", fileService.getAllFiles(user));
	    
		
		return "result";
	}
	
	
	@GetMapping("/viewFile/{fileId}")
	public ResponseEntity<ByteArrayResource> viewFile(@PathVariable(value = "fileId") int fileId, Model model) {
		
		File file = fileService.getFileById(fileId);
		
		return ResponseEntity.ok() 
				 .contentType(MediaType.parseMediaType(file.getContentType())).header(HttpHeaders.CONTENT_DISPOSITION,
			"attachment; filename=\"" + file.getFileName() + "\"").body(new 
				  ByteArrayResource(file.getFileData()));

	}
}
