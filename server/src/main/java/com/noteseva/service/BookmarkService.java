package com.noteseva.service;

import com.noteseva.model.Notes;
import com.noteseva.model.Organizer;
import com.noteseva.model.PYQ;
import com.noteseva.model.Users;
import com.noteseva.repository.NotesRepository;
import com.noteseva.repository.OrganizerRepository;
import com.noteseva.repository.PYQRepository;
import com.noteseva.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class BookmarkService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private PYQRepository pyqRepository;

    @Autowired
    private OrganizerRepository organizerRepository;

    public Users bookmarkNotes(String username, Integer notesId) {
        Users user = userService.findByUsername(username);
        Notes notes = notesRepository.findById(notesId).orElse(null);
        if(notes==null)
            return null;
        Set<Notes> bookmarkedNotes = user.getBookmarkedNotes();
        if(bookmarkedNotes==null) {
            bookmarkedNotes = new HashSet<>();
            user.setBookmarkedNotes(bookmarkedNotes);
        }
        bookmarkedNotes.add(notes);
        return userRepository.save(user);
    }

    public Users unBookmarkNotes(String username, Integer notesId) {
        Users user = userService.findByUsername(username);
        Notes notes = notesRepository.findById(notesId).orElse(null);
        if(notes==null)
            return null;
        Set<Notes> bookmarkedNotes = user.getBookmarkedNotes();
        if(bookmarkedNotes==null || !bookmarkedNotes.contains(notes))
            return null;
        bookmarkedNotes.remove(notes);
        return userRepository.save(user);
    }

    public Users bookmarkOrganizer(String username, Integer organizerId) {
        Users user = userService.findByUsername(username);
        Organizer organizer = organizerRepository.findById(organizerId).orElse(null);
        if(organizer==null)
            return null;
        user.getBookmarkedOrganizer().add(organizer);
        return userRepository.save(user);
    }

    public Users unBookmarkOrganizer(String username, Integer organizerId) {
        Users user = userService.findByUsername(username);
        Organizer organizer = organizerRepository.findById(organizerId).orElse(null);
        if(organizer==null)
            return null;
        Set<Organizer> bookmarkedOrganizer = user.getBookmarkedOrganizer();
        if(bookmarkedOrganizer ==null || !bookmarkedOrganizer.contains(organizer))
            return null;
        bookmarkedOrganizer.remove(organizer);
        return userRepository.save(user);
    }

    public Users bookmarkPYQ(String username, Integer pyqId) {
        Users user = userService.findByUsername(username);
        PYQ pyq = pyqRepository.findById(pyqId).orElse(null);
        if(pyq==null)
            return null;
        Set<PYQ> bookmarkedPYQ = user.getBookmarkedPYQ();
        if(bookmarkedPYQ==null) {
            bookmarkedPYQ = new HashSet<>();
            user.setBookmarkedPYQ(bookmarkedPYQ);
        }
        bookmarkedPYQ.add(pyq);
        return userRepository.save(user);
    }

    public Users unBookmarkPYQ(String username, Integer pyqId) {
        Users user = userService.findByUsername(username);
        PYQ pyq = pyqRepository.findById(pyqId).orElse(null);
        if(pyq==null)
            return null;
        Set<PYQ> bookmarkedPYQ = user.getBookmarkedPYQ();
        if(bookmarkedPYQ==null || !bookmarkedPYQ.contains(pyq))
            return null;
        bookmarkedPYQ.remove(pyq);
        return userRepository.save(user);
    }
}
