package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.modelServices.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.modelServices.FileUploadService;
import com.udacity.jwdnd.course1.cloudstorage.modelServices.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.modelServices.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class HomeController {

    private FileUploadService fileUploadService;
    private NoteService noteService;
    private UserService userService;
    private CredentialService credentialService;

    public HomeController(FileUploadService fileUploadService, NoteService noteService, UserService userService, CredentialService credentialService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileUploadService = fileUploadService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String getHomePage(Model model) {

        String show_value = (String) model.getAttribute("show");
        show_value = show_value != null ? show_value : "files";

        model.addAttribute("files", this.fileUploadService.getAllFiles(this.userService.getCurrentUserId()));
        model.addAttribute("notes", this.noteService.getAllNotes(this.userService.getCurrentUserId()));
        model.addAttribute("creds", this.credentialService.getAllCredential(this.userService.getCurrentUserId()));
        model.addAttribute("msg", "Welcome to Home Page");
        model.addAttribute("show", show_value);
        return "home";
    }




}
