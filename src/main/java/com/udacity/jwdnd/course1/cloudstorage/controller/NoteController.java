package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.modelServices.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.modelServices.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/home")
public class NoteController {

    private final UserService userService;
    private final NoteService noteService;

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping("/saveNote")
    public  RedirectView saveNote(@RequestParam("noteId") Integer noteId,
                                  @RequestParam("noteTitle") String noteTitle,
                                  @RequestParam("noteDescription") String noteDescription, RedirectAttributes redirectAttrs){
        System.out.println("NOTE SAVE BREAKPOINT HIT");
        if (noteId == null) {
            int result = this.noteService.saveNote(noteTitle, noteDescription, this.userService.getCurrentUserId());
        }
        else {
            int result = this.noteService.updateNote(noteId, noteTitle, noteDescription, this.userService.getCurrentUserId());
        }

      redirectAttrs.addFlashAttribute("show", "notes");
      return  new RedirectView("/home");
    }

    @GetMapping("/delete-note/{noteId}")
    public RedirectView deleteNote(@PathVariable("noteId") Integer noteId, RedirectAttributes redirectAttrs) {
        System.out.println("NOTE DELETE BREAKPOINT HIT");
        this.noteService.deleteNote(noteId);
        redirectAttrs.addFlashAttribute("show", "notes");
        return  new RedirectView("/home");
    }



}
