/**
 * @Author: Jobet P. Casquejo
 * @Date: 2025-31-8
 * @Version: 1.0
 * @Description: Repository interface for user data access.
 */
package com.edutrack.edutrack.repository;

import com.edutrack.edutrack.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by username.
     *
     * @param username the username of the user
     * @return the user with the given username
     */
    User findByUsername(String username);

    /**
     * Find a user by email.
     *
     * @param email the email of the user
     * @return the user with the given email
     */
    User findByEmail(String email);

    /**
     * Check if a user exists by username.
     *
     * @param username the username to check
     * @return true if a user with the given username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if a user exists by email.
     *
     * @param email the email to check
     * @return true if a user with the given email exists, false otherwise
     */
    boolean existsByEmail(String email);
}
