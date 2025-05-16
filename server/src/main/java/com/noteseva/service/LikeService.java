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
public class LikeService {

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

    public Users likeNotes(String username, Integer notesId) {
        Users user = userService.findByUsername(username);
        Notes notes = notesRepository.findById(notesId).orElse(null);
        if(notes==null)
            return null;
        Set<Notes> likedNotes = user.getLikedNotes();
        if(likedNotes ==null) {
            likedNotes = new HashSet<>();
            user.setLikedNotes(likedNotes);
        }
        likedNotes.add(notes);
        return userRepository.save(user);
    }

    public Users unLikeNotes(String username, Integer notesId) {
        Users user = userService.findByUsername(username);
        Notes notes = notesRepository.findById(notesId).orElse(null);
        if(notes==null)
            return null;
        Set<Notes> likedNotes = user.getLikedNotes();
        if(likedNotes ==null || !likedNotes.contains(notes))
            return null;
        likedNotes.remove(notes);
        return userRepository.save(user);
    }

    public Users likeOrganizer(String username, Integer organizerId) {
        Users user = userService.findByUsername(username);
        Organizer organizer = organizerRepository.findById(organizerId).orElse(null);
        if(organizer==null)
            return null;
        user.getLikedOrganizer().add(organizer);
        return userRepository.save(user);
    }

    public Users unLikeOrganizer(String username, Integer organizerId) {
        Users user = userService.findByUsername(username);
        Organizer organizer = organizerRepository.findById(organizerId).orElse(null);
        if(organizer==null)
            return null;
        Set<Organizer> likedOrganizer = user.getLikedOrganizer();
        if(likedOrganizer ==null || !likedOrganizer.contains(organizer))
            return null;
        likedOrganizer.remove(organizer);
        return userRepository.save(user);
    }

    public Users likePYQ(String username, Integer pyqId) {
        Users user = userService.findByUsername(username);
        PYQ pyq = pyqRepository.findById(pyqId).orElse(null);
        if(pyq==null)
            return null;
        Set<PYQ> likedPYQ = user.getLikedPYQ();
        if(likedPYQ ==null) {
            likedPYQ = new HashSet<>();
            user.setLikedPYQ(likedPYQ);
        }
        likedPYQ.add(pyq);
        return userRepository.save(user);
    }

    public Users unLikePYQ(String username, Integer pyqId) {
        Users user = userService.findByUsername(username);
        PYQ pyq = pyqRepository.findById(pyqId).orElse(null);
        if(pyq==null)
            return null;
        Set<PYQ> likedPYQ = user.getLikedPYQ();
        if(likedPYQ ==null || !likedPYQ.contains(pyq))
            return null;
        likedPYQ.remove(pyq);
        return userRepository.save(user);
    }
}

