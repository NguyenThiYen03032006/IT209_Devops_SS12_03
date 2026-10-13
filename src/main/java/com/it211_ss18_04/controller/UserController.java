package com.it211_ss18_04.controller;

import com.it211_ss18_04.dto.RoleUpdateRequest;
import com.it211_ss18_04.entity.User;
import com.it211_ss18_04.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public User updateRole(
            @PathVariable Long id,
            @RequestBody RoleUpdateRequest request) {

        return userService.updateRole(
                id,
                request.getRole()
        );
    }

    @GetMapping("/me")
    public User me() {
        return userService.getCurrentUser();
    }
}