package com.udacity.jwdnd.course1.cloudstorage.modelServices;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Note getNote(String noteTitle,String noteDescription, int userId) {
        Note note = new Note();
        note.setNoteTitle(noteTitle);
        note.setNoteDescription(noteDescription);
        note.setUserId(userId);
        return note;
    }

    public int saveNote(String noteTitle,String noteDescription, int userId) {
        Note note = getNote(noteTitle, noteDescription, userId);
        return this.noteMapper.insertNote(note);
    }

    public ArrayList<Note> getAllNotes(int userId) {
        return this.noteMapper.getNoteListingByUserId(userId);
    }

    public void deleteNote(int noteId) {
        this.noteMapper.deleteNote(noteId);
    }

    public int updateNote(int noteId, String noteTitle, String noteDescription, int userId) {
        Note note =  getNote(noteTitle, noteDescription, userId);
        note.setNoteId(noteId);
        return this.noteMapper.updateNote(note);
    }

}
