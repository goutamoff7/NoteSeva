package com.noteseva.controller;

import com.noteseva.DTO.UsersDTO;
import com.noteseva.Pagination.PageResponse;
import com.noteseva.model.Users;
import com.noteseva.repository.UserRepository;
import com.noteseva.service.AdminService;
import com.noteseva.service.DTOService;
import com.noteseva.service.EmailService;
import com.noteseva.service.UtilityService;
import com.noteseva.validation.RegisterValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs", description = "Create admin, Get all user info")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UtilityService utilityService;

    @Autowired
    DTOService dtoService;

    @Autowired
    EmailService emailService;

    //localhost:8080/admin/register
    @Operation(summary = "Admin Registration")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated(RegisterValidation.class) @RequestBody UsersDTO userDTO) {
        try {
            Users user = dtoService.getUser(userDTO);
            String username = utilityService.extractUsernameFromEmail(user.getEmail());
            if (userRepository.findByUsername(username)==null) {
                Users registeredAdmin = adminService.registerAdmin(user);
                if(registeredAdmin!=null) {
                    emailService.sendSuccessEmail(registeredAdmin.getEmail(),
                            registeredAdmin.getName());
                    return new ResponseEntity<>(registeredAdmin, HttpStatus.CREATED);
                }
                else
                    return new ResponseEntity<>("Admin Registration Unsuccessful",HttpStatus.SERVICE_UNAVAILABLE);
            }
            return new ResponseEntity<>("Admin already exist",HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/admin/get-all-user?
//    pageNumber=0&
//    pageSize=12&
//    sortBy=id&
//    sortingOrder=ASC
    @Operation(summary = "Get all User information")
    @GetMapping("/get-all-user")
    public ResponseEntity<?> getAllUser(
            @RequestParam(required = false,defaultValue = "0") int pageNumber,
            @RequestParam(required = false,defaultValue = "12") int pageSize,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(required = false,defaultValue = "ASC") String sortingOrder) {
        try {
            PageResponse<Users> allUser = adminService.getAllUser(
                    pageNumber,pageSize,sortBy,sortingOrder);
            if (allUser != null)
                return new ResponseEntity<>(allUser, HttpStatus.OK);
            return new ResponseEntity<>("No user available",
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>("Something Went Wrong!!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
