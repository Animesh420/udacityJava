package com.udacity.jwdnd.course1.cloudstorage.modelServices;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileUploadMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class FileUploadService {

    private final FileUploadMapper fileUploadMapper;
    private final HashService hashService;

    public FileUploadService(FileUploadMapper fileUploadMapper, HashService hashService) {
        this.fileUploadMapper = fileUploadMapper;
        this.hashService = hashService;
    }

    public int uploadFile(MultipartFile multipartFile, Integer userId) throws IOException {

        File exists = this.fileUploadMapper.doesFileExist(multipartFile.getOriginalFilename(), userId);

        if (exists == null) {
            File file = new File(null,
                    multipartFile.getOriginalFilename(),
                    multipartFile.getContentType(),
                    String.valueOf(multipartFile.getSize()), userId, multipartFile.getBytes());
            return this.fileUploadMapper.insertFile(file);
        }
        return 0;
    }


    public File getFileByFileId(Integer fileId) {
        return this.fileUploadMapper.getFileByFileId(fileId);
    }

    public ArrayList<File> getAllFiles(Integer userId){
        return this.fileUploadMapper.getFileListingByUserId(userId);
    }

    public void deleteFile(Integer fileId) {
        this.fileUploadMapper.deleteFile(fileId);
    }


}
