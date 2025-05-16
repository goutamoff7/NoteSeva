package com.noteseva.controller;

import com.noteseva.model.Users;
import com.noteseva.service.LikeService;
import com.noteseva.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@Tag(name = "Like APIs", description = "Like or un-Like Notes, Organizer, and PYQ")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Like a notes")
    @PostMapping("/notes/{notesId}")
    public ResponseEntity<?> likeNotes(@PathVariable Integer notesId) {
        try {
            Users user = likeService.likeNotes(userService.getCurrentUsername(),
                    notesId);
            if (user == null)
                return new ResponseEntity<>("Failed to Like Notes",
                        HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>("Notes Liked Successfully",
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "UnLike a notes")
    @DeleteMapping("/notes/{notesId}")
    public ResponseEntity<?> unLikeNotes(@PathVariable Integer notesId) {
        try {
            Users user = likeService.unLikeNotes(userService.getCurrentUsername(),
                    notesId);
            if (user == null)
                return new ResponseEntity<>("Failed to Un-Like Notes",
                        HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>("Notes Un-Liked Successfully",
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Like an organizer")
    @PostMapping("/organizer/{organizerId}")
    public ResponseEntity<?> likeOrganizer(@PathVariable Integer organizerId) {
        try {
            Users user = likeService.likeOrganizer(userService.getCurrentUsername(),
                    organizerId);
            if (user == null)
                return new ResponseEntity<>("Failed to Like Organizer",
                        HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>("Organizer Liked Successfully",
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "UnLike an organizer")
    @DeleteMapping("/organizer/{organizerId}")
    public ResponseEntity<?> unLikeOrganizer(@PathVariable Integer organizerId) {
        try {
            Users user = likeService.unLikeOrganizer(userService.getCurrentUsername(),
                    organizerId);
            if (user == null)
                return new ResponseEntity<>("Failed to Un-Like Organizer",
                        HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>("Organizer Un-Liked Successfully",
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Like a PYQ")
    @PostMapping("/pyq/{pyqId}")
    public ResponseEntity<?> likePYQ(@PathVariable Integer pyqId) {
        try {
            Users user = likeService.likePYQ(userService.getCurrentUsername(),
                    pyqId);
            if (user == null)
                return new ResponseEntity<>("Failed to Like PYQ",
                        HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>("PYQ Liked Successfully",
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "UnLike a PYQ")
    @DeleteMapping("/pyq/{pyqId}")
    public ResponseEntity<?> unLikePYQ(@PathVariable Integer pyqId) {
        try {
            Users user = likeService.unLikePYQ(userService.getCurrentUsername(),
                    pyqId);
            if (user == null)
                return new ResponseEntity<>("Failed to Un-Like PYQ",
                        HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>("PYQ Un-Liked Successfully",
                    HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
