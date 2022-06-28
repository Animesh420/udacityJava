package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.modelServices.FileUploadService;
import com.udacity.jwdnd.course1.cloudstorage.modelServices.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class FileController {

    private FileUploadService fileUploadService;
    private UserService userService;

    public FileController(FileUploadService fileUploadService, UserService userService) {
        this.userService = userService;
        this.fileUploadService = fileUploadService;
    }


    @PostMapping("/uploadFile")
    public RedirectView uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, RedirectAttributes redirectAttrs)
            throws IOException {
        System.out.println("FILE UPLOAD BREAKPOINT HIT");
        if (fileUpload.getSize() != 0 ) {
            Integer currentUserId = this.userService.getCurrentUserId();
            int entered = this.fileUploadService.uploadFile(fileUpload, currentUserId);
        }

        redirectAttrs.addFlashAttribute("show", "files");
        return new RedirectView("/home");
    }

    @GetMapping(value = "/view-file/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    ResponseEntity<byte[]> viewData(@PathVariable Integer fileId){
        File file = this.fileUploadService.getFileByFileId(fileId);

        String fileName = file.getFileName();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+fileName+"\"")
                .body(file.getFileData());
    }

    @GetMapping("/delete-file/{fileId}")
    public RedirectView deleteFile(@PathVariable("fileId") Integer fileId, RedirectAttributes redirectAttrs) {
        System.out.println("FILE DELETE BREAKPOINT HIT");
        this.fileUploadService.deleteFile(fileId);
        redirectAttrs.addFlashAttribute("show", "files");
        return new RedirectView("/home");
    }
}
