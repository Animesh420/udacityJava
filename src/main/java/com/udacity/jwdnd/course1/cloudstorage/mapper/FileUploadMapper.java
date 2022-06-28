package com.udacity.jwdnd.course1.cloudstorage.mapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;
import java.util.ArrayList;

@Mapper
public interface FileUploadMapper {

    //a specific file
    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File getFileByFileName(String fileName);

    //See ArrayListing of files
    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    ArrayList<File> getFileListingByUserId(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFileByFileId(Integer fileId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteFile(Integer fileId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Select("SELECT * FROM FILES WHERE filename = #{filename} AND userid = #{userId}")
    File doesFileExist(String filename, Integer userId);
}
