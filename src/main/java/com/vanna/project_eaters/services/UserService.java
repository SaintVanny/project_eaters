package com.vanna.project_eaters.services;

import com.vanna.project_eaters.exceptions.UsernameAlreadyExistsException;
//import com.vanna.project_eater_.mapper.UserMapper;
import com.vanna.project_eaters.models.enums.Role;
import com.vanna.project_eaters.models.entity.User;
import com.vanna.project_eaters.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {
//    private final BlacklistedTokenRepository blacklistedTokenRepository;
//    private final PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
//    private UserMapper userMapper;
//    private JwtUtil jwtUtil;

    public User save(User user) {
        return userRepository.save(user);
    }



    @Transactional
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException("Username is already taken");
        }
        if (user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username must not be empty or blank");
        }
        return save(user);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }
}
