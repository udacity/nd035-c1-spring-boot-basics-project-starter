package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm {

    private String title;
    private String description;
    private String noteId;

    public NoteForm() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return noteId;
    }

    public void setId(String id) {
        this.noteId = id;
    }
}
