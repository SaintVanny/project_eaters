package com.vanna.project_eaters.services;

import com.vanna.project_eaters.exceptions.UsernameAlreadyExistsException;
//import com.vanna.project_eater_.mapper.UserMapper;
import com.vanna.project_eaters.models.Role;
import com.vanna.project_eaters.models.User;
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
//        User user = userMapper.toEntity(user);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        User savedUser = userRepository.save(user);
//        String token = jwtUtil.generateToken(savedUser.getUsername());

//        return new UserResponseDTO(token,savedUser.getUsername());

        return save(user);
    }

//    @Transactional
//    public UserResponseDTO login(LoginDTO loginDTO) {
//        User user = userRepository.findByUsername(loginDTO.getUsername())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
//        String token = jwtUtil.generateToken(user.getUsername());
//
//        return new UserResponseDTO(token,user.getUsername());
//    }
//
//    @Transactional
//    public void logout(String token) {
//        log.info("Attempting to blacklist token: " + token);
//
//        if (blacklistedTokenRepository.existsByToken(token)) {
//            throw new RuntimeException("Token is already blacklisted");
//        }
//        // Получаем дату истечения токена
//        LocalDateTime expirationDate = jwtUtil.getExpirationDate(token);
//        log.info("Token expiration date: " + expirationDate);
//
//        // Добавляем токен в черный список
//        blacklistedTokenRepository.save(new BlacklistedToken(token, expirationDate));
//        log.info("Token added to blacklist successfully");
//
//    }

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
