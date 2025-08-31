/**
 * @Author: Jobet P. Casquejo
 * @Date: 2025-31-8
 * @Version: 1.0
 * @Description: Service class for user authentication and authorization.
 */
package com.edutrack.edutrack.services;

import com.edutrack.edutrack.models.User;
import com.edutrack.edutrack.repository.UserRepository;
import com.edutrack.edutrack.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Register a new user.
     * Password will be encoded before saving.
     */
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Authenticate user credentials and return JWT token.
     */
    public String login(String username, String password) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // Generate JWT token for authenticated user
        return jwtTokenProvider.generateToken(username);
    }

    /**
     * Fetch user details by username.
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
