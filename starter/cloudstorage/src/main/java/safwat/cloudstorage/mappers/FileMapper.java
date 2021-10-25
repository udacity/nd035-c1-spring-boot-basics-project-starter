package safwat.cloudstorage.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import safwat.cloudstorage.model.File;
import safwat.cloudstorage.model.User;

@Mapper
public interface FileMapper {
	
	@Select("SELECT * FROM FILES WHERE filename = #{fileName}")
	File findFileByName(String fileName);
	
	@Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
	File findFileById(int fileId);
	
	@Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    File getFileByFileId(Integer fileId);
	
	@Select("SELECT * FROM FILES WHERE userid = #{userId}")
	List<File> findAllFile(User user);
	
	
	@Insert("INSERT INTO FILES (filename, contenttype, filesize, filedata, userid)"
			+ "VALUES (#{fileName}, #{contentType}, #{fileSize}, #{fileData}, #{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "fileId")
	int insertFile(File file);
	
	
	@Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
	void delete(int fileId); 
	
}
