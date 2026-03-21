package com.trier.trier_report.dao;

import com.trier.trier_report.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Returns true if a user with the given email exists
    boolean existsByEmail(String email);

    // Optional: to fetch the user if needed
    Optional<User> findByEmail(String email);
}
