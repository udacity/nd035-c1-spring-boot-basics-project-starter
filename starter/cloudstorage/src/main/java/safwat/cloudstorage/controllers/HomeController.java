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
	public String getHomePage(Note note, Model model) {
		model.addAttribute("notesList", noteService.getAllNotes());
		model.addAttribute("credentialsList", credentialsService.getAllCredentials());
		model.addAttribute("filesList", fileService.getAllFiles());
		
		return "home";
	}
	
	
	@PostMapping
	public void postNotePage(Note note, Credentials credentials, Authentication auth, Model model) {
		
		/*System.out.println(note.getNoteTitle());
		System.out.println(note.getNoteDescription());
		System.out.println(note.getNoteId());*/
		
		
		if(note.getNoteDescription() != null || note.getNoteTitle() != null) {
			
			if(note.getNoteId() == null) {
				User user = userService.getUserByUserName(auth.getName());
				
				System.out.println("username : " + auth.getName()); 
				System.out.println("user : " + user);
				
				note.setUserId(user.getUserId());
				noteService.createNote(note);
				
			}
			else {
				noteService.updateNote(note);
			}
			
			
		}
		
		if(credentials.getUrl() != null || credentials.getUserName() != null
				|| credentials.getPassword() != null) {
			
			System.out.println("id " + credentials.getCredentialId());
			
			if(credentials.getCredentialId() == null) {
				User user = userService.getUserByUserName(auth.getName());
				credentials.setUserId(user.getUserId());
				credentialsService.createCredentials(credentials);
			}
			else {
				credentialsService.updateCredentials(credentials);
			}
			
			
		}
		if(credentials.getCredentialId() != null) {
			model.addAttribute("defPassword", credentialsService.getOriginalPass(credentials));
		}
		model.addAttribute("notesList", noteService.getAllNotes());
		model.addAttribute("credentialsList", credentialsService.getAllCredentials());
		
	}
	
	@GetMapping("/deleteNote/{noteid}")

	public String deleteNote(@PathVariable(value = "noteid") int noteId, Authentication authentication, Note note, Model model){
		
		System.out.println("delete ---> " + note.getNoteTitle());
		
		noteService.deleteNote(noteId); 

		return "redirect:/home";

	}
	
	@GetMapping("/deleteCredential/{credentialId}")
	public String deleteCredential(@PathVariable(value = "credentialId") int credentialId, Model model) {
		credentialsService.deleteCredential(credentialId);
		
		return "redirect:/home";
	}
	
	@GetMapping("/deleteFile/{fileId}")
	public String deleteFile(@PathVariable(value = "fileId") int fileId, Model model) {
		
		fileService.deleteFile(fileId);
		
		return "redirect:/home";
	}
	
	
	@PostMapping("/files")
	public String uploadFiles(@RequestParam("fileUpload") MultipartFile fileUpload, Model model ) throws IOException {
		File file = new File(null, null, null, null, null, null);
		file.setContentType(fileUpload.getContentType());
		file.setFileData(fileUpload.getBytes());
		file.setFileName(fileUpload.getOriginalFilename());
		file.setFileSize(fileUpload.getSize());
		
		fileService.saveFile(file);
		
		System.out.println(fileUpload.getContentType());
	    System.out.println(fileUpload.getBytes());
	    System.out.println(fileUpload.getOriginalFilename());
	    System.out.println(fileUpload.getSize());
	    
	    model.addAttribute("filesList", fileService.getAllFiles());
		
		return "redirect:/home";
	}
	
	
	@GetMapping("/viewFile/{fileId}")
	public ResponseEntity<ByteArrayResource> viewFile(@PathVariable(value = "fileId") int fileId, Model model) {
		
		File file = fileService.getFileById(fileId);
		
		return ResponseEntity.ok() 
				 .contentType(MediaType.parseMediaType(file.getContentType())).header(HttpHeaders.CONTENT_DISPOSITION,
			"attachment; filename=\"" + file.getFileName() + "\"").body(new 
				  ByteArrayResource(file.getFileData()));
		//return "redirect:/home";
	}
}
