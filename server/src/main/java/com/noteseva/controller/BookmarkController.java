package com.noteseva.controller;

import com.noteseva.model.Users;
import com.noteseva.service.BookmarkService;
import com.noteseva.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookmark")
@Tag(name = "Bookmark APIs", description = "Bookmark or un Bookmark Notes, Organizer, and PYQ")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Bookmark a notes")
    @PostMapping("/notes/{notesId}")
    public ResponseEntity<?> bookmarkNotes(@PathVariable Integer notesId) {
        try {
            Users user = bookmarkService.bookmarkNotes(userService.getCurrentUsername(),
                    notesId);
            if (user == null)
                return new ResponseEntity<>("Failed to Bookmark Notes",
                        HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>("Notes added to Bookmark",
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "UnBookmark a notes")
    @DeleteMapping("/notes/{notesId}")
    public ResponseEntity<?> unBookmarkNotes(@PathVariable Integer notesId) {
        try {
            Users user = bookmarkService.unBookmarkNotes(userService.getCurrentUsername(),
                    notesId);
            if (user == null)
                return new ResponseEntity<>("Failed to UnBookmark Notes",
                        HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>("Notes Removed from Bookmark",
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Bookmark an organizer")
    @PostMapping("/organizer/{organizerId}")
    public ResponseEntity<?> bookmarkOrganizer(@PathVariable Integer organizerId) {
        try {
            Users user = bookmarkService.bookmarkOrganizer(userService.getCurrentUsername(),
                    organizerId);
            if (user == null)
                return new ResponseEntity<>("Failed to Bookmark Organizer",
                        HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>("Organizer added to Bookmark",
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "UnBookmark an organizer")
    @DeleteMapping("/organizer/{organizerId}")
    public ResponseEntity<?> unBookmarkOrganizer(@PathVariable Integer organizerId) {
        try {
            Users user = bookmarkService.unBookmarkOrganizer(userService.getCurrentUsername(),
                    organizerId);
            if (user == null)
                return new ResponseEntity<>("Failed to UnBookmark Organizer",
                        HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>("Organizer Removed from Bookmark",
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Bookmark a PYQ")
    @PostMapping("/pyq/{pyqId}")
    public ResponseEntity<?> bookmarkPYQ(@PathVariable Integer pyqId) {
        try {
            Users user = bookmarkService.bookmarkPYQ(userService.getCurrentUsername(),
                    pyqId);
            if (user == null)
                return new ResponseEntity<>("Failed to Bookmark PYQ",
                        HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>("PYQ added to Bookmark",
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "UnBookmark a PYQ")
    @DeleteMapping("/pyq/{pyqId}")
    public ResponseEntity<?> unBookmarkPYQ(@PathVariable Integer pyqId) {
        try {
            Users user = bookmarkService.unBookmarkPYQ(userService.getCurrentUsername(),
                    pyqId);
            if (user == null)
                return new ResponseEntity<>("Failed to UnBookmark PYQ",
                        HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>("PYQ Removed from Bookmark",
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
