package safwat.cloudstorage.model;

public class Note {
	private Integer noteId;
	private String noteTitle;
	public String noteDescription;
	Integer userId;
	
	public Note(Integer id, String noteTitle, String noteDescription, Integer userId) {
	    this.noteId= id;
	    this.noteTitle = noteTitle;
	    this.noteDescription = noteDescription;
	    this.userId = userId;
	}
	
	/*public Note( String noteTitle, String noteDescription, Integer userId) {
		//this.noteId = noteId;
		this.noteTitle = noteTitle;
		this.noteDescription = noteDescription;
		this.userId = userId;
	}*/
	public Integer getNoteId() {
		return noteId;
	}
	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}
	public String getNoteTitle() {
		return noteTitle;
	}
	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}
	public String getNoteDescription() {
		return noteDescription;
	}
	public void setNoteDescribtion(String noteDescription) {
		this.noteDescription = noteDescription;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	

}
