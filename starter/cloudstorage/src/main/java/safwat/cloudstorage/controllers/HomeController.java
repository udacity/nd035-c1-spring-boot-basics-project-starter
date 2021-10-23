package safwat.cloudstorage.controllers;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import safwat.cloudstorage.model.Note;
import safwat.cloudstorage.services.NoteService;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	NoteService noteService;
	
	public HomeController(NoteService noteService) {
		// TODO Auto-generated constructor stub
		this.noteService = noteService;
	}
	
	
	@GetMapping
	public String getHomePage(Note note, Model model) {
		model.addAttribute("notesList", noteService.getAllNotes());
		
		return "home";
	}
	
	
	@PostMapping
	public void postNotePage(Note note, Authentication auth, Model model) {
		
		/*System.out.println(note.getNoteTitle());
		System.out.println(note.getNoteDescription());
		System.out.println(note.getNoteId());*/
		
		
		if(note.getNoteDescription() != null || note.getNoteTitle() != null) {
			
			if(note.getNoteId() == null) {
				noteService.createNote(note);
				
			}
			else {
				noteService.updateNote(note);
			}
			
			
		}
		model.addAttribute("notesList", noteService.getAllNotes());
		
	}
	
	@GetMapping("/deleteNote/{noteid}")

	public String deleteNote(@PathVariable(value = "noteid") int noteId, Authentication authentication, Note note, Model model){
		
		System.out.println("delete ---> " + note.getNoteTitle());
		
		noteService.deleteNote(noteId); 

		return "redirect:/home";

	}
}
