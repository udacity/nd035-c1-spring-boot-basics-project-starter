package safwat.cloudstorage.model;

public class File {
	Integer fileId;
	String fileName;
	String contentType;
	Long fileSize;
	Integer userId;
	byte[] fileData;
	
	public File(Integer fileId, String fileName, String contentType, Long fileSize, Integer userId, byte[] fileData) {
		this.fileId = fileId;
		this.fileName = fileName;
		this.contentType = contentType;
		this.fileSize = fileSize;
		this.userId = userId;
		this.fileData = fileData;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] file) {
		this.fileData = file;
	}
	
	
}
