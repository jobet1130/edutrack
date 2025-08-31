/**
 * @Author: Jobet P. Casquejo
 * @Date: 2025-31-8
 * @Version: 1.0
 * @Description: Custom implementation of Spring Security's UserDetailsService.
 */
package com.edutrack.edutrack.services;

import com.edutrack.edutrack.models.User;
import com.edutrack.edutrack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Custom implementation of Spring Security's UserDetailsService.
 * <p>
 * This service loads user-specific data during authentication.
 * It retrieves the user from the database using UserRepository and
 * maps the user's role to Spring Security's GrantedAuthority.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Locates the user based on the username.
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated UserDetails object (never {@code null})
     * @throws UsernameNotFoundException if the user could not be found or has no GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from database
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Map user's role to GrantedAuthority
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());

        // Return Spring Security User object
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                Collections.singleton(authority)
        );
    }
}
