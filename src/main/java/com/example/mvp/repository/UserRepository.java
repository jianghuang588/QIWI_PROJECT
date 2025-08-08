package com.example.mvp.repository;

import com.example.mvp.database_table.User;
import com.example.mvp.enums.Major;
import com.example.mvp.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// UserRepository
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(UserRole role);
    List<User> findByMajor(Major major);
    boolean existsByEmail(String email);
}