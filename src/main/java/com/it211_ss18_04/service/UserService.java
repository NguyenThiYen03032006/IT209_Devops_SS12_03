package com.it211_ss18_04.service;

import com.it211_ss18_04.entity.Role;
import com.it211_ss18_04.entity.User;
import com.it211_ss18_04.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(
            UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User updateRole(
            Long id,
            Role role) {

        User user = userRepository
                .findById(id)
                .orElseThrow();

        user.setRole(role);

        return userRepository.save(user);
    }

    public User getCurrentUser() {

        String username =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName();

        return userRepository
                .findByUsername(username)
                .orElseThrow();
    }
}