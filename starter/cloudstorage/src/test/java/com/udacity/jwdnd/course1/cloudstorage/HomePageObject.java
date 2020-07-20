package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePageObject {

    public HomePageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "savedContinue")
    private WebElement saveContinueOnResult;

    public WebElement getSaveContinueOnResult() {
        return saveContinueOnResult;
    }

    public void setSaveContinueOnResult(WebElement saveContinueOnResult) {
        this.saveContinueOnResult = saveContinueOnResult;
    }

    @FindBy(id = "logOutButton")
    private WebElement logOutButton;

    @FindBy(id = "logoutDiv")
    private WebElement logoutDiv;

    @FindBy(id = "nav-tab")
    private WebElement navTabs;

    @FindBy(id = "nav-files-tab")
    private WebElement navFilesTab;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "fileUpload")
    private WebElement fileUpload;

    @FindBy(id = "uploadFileButton")
    private WebElement uploadFileButton;

    @FindBy(id = "fileTable")
    private WebElement fileTable;

    @FindBy(id = "allFiles")
    private WebElement allFiles;

    @FindBy(id = "viewFileButton")
    private WebElement viewFileButton;

    @FindBy(id = "deleteFileButton")
    private WebElement deleteFileButton;

    @FindBy(id = "showNoteModelButton")
    private WebElement showNoteModelButton;

    @FindBy(id = "userTable")
    private WebElement userTable;

    @FindBy(id = "allNotes")
    private WebElement allNotes;

    @FindBy(id = "editNote")
    private WebElement editNote;

    @FindBy(id = "deleteNote")
    private WebElement deleteNote;

    @FindBy(id = "noteModal")
    private WebElement noteModal;

    @FindBy(id = "noteModalLabel")
    private WebElement noteModalLabel;

    @FindBy(id = "closeNoteButtonModal")
    private WebElement closeNoteButtonModal;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmit;

    @FindBy(id = "closeNoteButtonFooter")
    private WebElement closeNoteButtonFooter;

    @FindBy(id = "submitNoteButtonFooter")
    private WebElement submitNoteButtonFooter;

    @FindBy(id = "showCredentialsModal")
    private WebElement showCredentialsModal;

    @FindBy(id = "allCredentials")
    private WebElement allCredentials;

    @FindBy(id = "editCredential")
    private WebElement editCredential;

    @FindBy(id = "deleteCredential")
    private WebElement deleteCredential;

    @FindBy(id = "closeCredentialModal")
    private WebElement closeCredentialModal;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmit;

    @FindBy(id = "closeCredentialFooter")
    private WebElement closeCredentialFooter;

    @FindBy(id = "saveCredentialFooter")
    private WebElement saveCredentialFooter;

    public WebElement getLogOutButton() {
        return logOutButton;
    }

    public void setLogOutButton(WebElement logOutButton) {
        this.logOutButton = logOutButton;
    }

    public WebElement getLogoutDiv() {
        return logoutDiv;
    }

    public void setLogoutDiv(WebElement logoutDiv) {
        this.logoutDiv = logoutDiv;
    }

    public WebElement getNavTabs() {
        return navTabs;
    }

    public void setNavTabs(WebElement navTabs) {
        this.navTabs = navTabs;
    }

    public WebElement getNavFilesTab() {
        return navFilesTab;
    }

    public void setNavFilesTab(WebElement navFilesTab) {
        this.navFilesTab = navFilesTab;
    }

    public WebElement getNavNotesTab() {
        return navNotesTab;
    }

    public void setNavNotesTab(WebElement navNotesTab) {
        this.navNotesTab = navNotesTab;
    }

    public WebElement getNavCredentialsTab() {
        return navCredentialsTab;
    }

    public void setNavCredentialsTab(WebElement navCredentialsTab) {
        this.navCredentialsTab = navCredentialsTab;
    }

    public WebElement getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(WebElement fileUpload) {
        this.fileUpload = fileUpload;
    }

    public WebElement getUploadFileButton() {
        return uploadFileButton;
    }

    public void setUploadFileButton(WebElement uploadFileButton) {
        this.uploadFileButton = uploadFileButton;
    }

    public WebElement getFileTable() {
        return fileTable;
    }

    public void setFileTable(WebElement fileTable) {
        this.fileTable = fileTable;
    }

    public WebElement getAllFiles() {
        return allFiles;
    }

    public void setAllFiles(WebElement allFiles) {
        this.allFiles = allFiles;
    }

    public WebElement getViewFileButton() {
        return viewFileButton;
    }

    public void setViewFileButton(WebElement viewFileButton) {
        this.viewFileButton = viewFileButton;
    }

    public WebElement getDeleteFileButton() {
        return deleteFileButton;
    }

    public void setDeleteFileButton(WebElement deleteFileButton) {
        this.deleteFileButton = deleteFileButton;
    }

    public WebElement getShowNoteModelButton() {
        return showNoteModelButton;
    }

    public void setShowNoteModelButton(WebElement showNoteModelButton) {
        this.showNoteModelButton = showNoteModelButton;
    }

    public WebElement getUserTable() {
        return userTable;
    }

    public void setUserTable(WebElement userTable) {
        this.userTable = userTable;
    }

    public WebElement getAllNotes() {
        return allNotes;
    }

    public void setAllNotes(WebElement allNotes) {
        this.allNotes = allNotes;
    }

    public WebElement getEditNote() {
        return editNote;
    }

    public void setEditNote(WebElement editNote) {
        this.editNote = editNote;
    }

    public WebElement getDeleteNote() {
        return deleteNote;
    }

    public void setDeleteNote(WebElement deleteNote) {
        this.deleteNote = deleteNote;
    }

    public WebElement getNoteModal() {
        return noteModal;
    }

    public void setNoteModal(WebElement noteModal) {
        this.noteModal = noteModal;
    }

    public WebElement getNoteModalLabel() {
        return noteModalLabel;
    }

    public void setNoteModalLabel(WebElement noteModalLabel) {
        this.noteModalLabel = noteModalLabel;
    }

    public WebElement getCloseNoteButtonModal() {
        return closeNoteButtonModal;
    }

    public void setCloseNoteButtonModal(WebElement closeNoteButtonModal) {
        this.closeNoteButtonModal = closeNoteButtonModal;
    }

    public WebElement getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(WebElement noteTitle) {
        this.noteTitle = noteTitle;
    }

    public WebElement getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(WebElement noteDescription) {
        this.noteDescription = noteDescription;
    }

    public WebElement getNoteSubmit() {
        return noteSubmit;
    }

    public void setNoteSubmit(WebElement noteSubmit) {
        this.noteSubmit = noteSubmit;
    }

    public WebElement getCloseNoteButtonFooter() {
        return closeNoteButtonFooter;
    }

    public void setCloseNoteButtonFooter(WebElement closeNoteButtonFooter) {
        this.closeNoteButtonFooter = closeNoteButtonFooter;
    }

    public WebElement getSubmitNoteButtonFooter() {
        return submitNoteButtonFooter;
    }

    public void setSubmitNoteButtonFooter(WebElement submitNoteButtonFooter) {
        this.submitNoteButtonFooter = submitNoteButtonFooter;
    }

    public WebElement getShowCredentialsModal() {
        return showCredentialsModal;
    }

    public void setShowCredentialsModal(WebElement showCredentialsModal) {
        this.showCredentialsModal = showCredentialsModal;
    }

    public WebElement getAllCredentials() {
        return allCredentials;
    }

    public void setAllCredentials(WebElement allCredentials) {
        this.allCredentials = allCredentials;
    }

    public WebElement getEditCredential() {
        return editCredential;
    }

    public void setEditCredential(WebElement editCredential) {
        this.editCredential = editCredential;
    }

    public WebElement getDeleteCredential() {
        return deleteCredential;
    }

    public void setDeleteCredential(WebElement deleteCredential) {
        this.deleteCredential = deleteCredential;
    }

    public WebElement getCloseCredentialModal() {
        return closeCredentialModal;
    }

    public void setCloseCredentialModal(WebElement closeCredentialModal) {
        this.closeCredentialModal = closeCredentialModal;
    }

    public WebElement getCredentialUrl() {
        return credentialUrl;
    }

    public void setCredentialUrl(WebElement credentialUrl) {
        this.credentialUrl = credentialUrl;
    }

    public WebElement getCredentialUsername() {
        return credentialUsername;
    }

    public void setCredentialUsername(WebElement credentialUsername) {
        this.credentialUsername = credentialUsername;
    }

    public WebElement getCredentialPassword() {
        return credentialPassword;
    }

    public void setCredentialPassword(WebElement credentialPassword) {
        this.credentialPassword = credentialPassword;
    }

    public WebElement getCredentialSubmit() {
        return credentialSubmit;
    }

    public void setCredentialSubmit(WebElement credentialSubmit) {
        this.credentialSubmit = credentialSubmit;
    }

    public WebElement getCloseCredentialFooter() {
        return closeCredentialFooter;
    }

    public void setCloseCredentialFooter(WebElement closeCredentialFooter) {
        this.closeCredentialFooter = closeCredentialFooter;
    }

    public WebElement getSaveCredentialFooter() {
        return saveCredentialFooter;
    }

    public void setSaveCredentialFooter(WebElement saveCredentialFooter) {
        this.saveCredentialFooter = saveCredentialFooter;
    }
}
