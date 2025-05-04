package com.noteseva.service;

import com.noteseva.DTO.UserSummaryDTO;
import com.noteseva.Pagination.PageResponse;
import com.noteseva.constants.Role;
import com.noteseva.model.Users;
import com.noteseva.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UtilityService utilityService;

    @Autowired
    DTOService dtoService;

    public Users registerAdmin(Users user) {
        user.setUsername(utilityService.extractUsernameFromEmail(user.getEmail()));  // username = substring of email id, before @ symbol
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_ADMIN);
        return userRepository.save(user);
    }

    public PageResponse<UserSummaryDTO> getAllUser(
            int pageNumber,
            int pageSize,
            String sortBy,
            String sortingOrder) {
        Sort sort = sortingOrder.equalsIgnoreCase("ASC") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Users> pageUsers = userRepository.findAll(pageable);
        if(!pageUsers.getContent().isEmpty()) {
            List<UserSummaryDTO> userSummaryDTOList = pageUsers
                    .getContent().stream()
                    .map(user -> dtoService.convertToUserSummaryDTO(user))
                    .toList();
            return PageResponse.getPageResponseDTO(pageUsers, userSummaryDTOList);
        }
        return null;
    }
}
