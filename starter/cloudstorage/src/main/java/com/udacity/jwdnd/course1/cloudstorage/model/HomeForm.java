package com.udacity.jwdnd.course1.cloudstorage.model;

public class HomeForm {
    private int noteId;
    private String noteTitle;
    private String noteDescription;
    private int userId;
    private String noteAction;

    public HomeForm(String noteTitle, String noteDescription) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        if ( ! noteId.isEmpty())
            this.noteId = Integer.parseInt(noteId);
    }

    public String getNoteAction() {
        return noteAction;
    }

    public void setNoteAction(String noteAction) {
        this.noteAction = noteAction;
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

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
