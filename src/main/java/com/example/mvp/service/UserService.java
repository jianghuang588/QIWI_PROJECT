package com.example.mvp.service;

import com.example.mvp.database_table.User;
import com.example.mvp.dto.UserRegistrationDto;
import com.example.mvp.enums.UserRole;
import com.example.mvp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(UserRegistrationDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User(dto.getName(), dto.getEmail(), dto.getMajor(), dto.getRole());
        user.setTermsAccepted(dto.getTermsAccepted());
        user.setPrivacyAccepted(dto.getPrivacyAccepted());

        // ADD NEW FIELDS
        if (dto.getFirstName() != null) {
            user.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            user.setLastName(dto.getLastName());
        }
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }
        // Set name field as combination if separate names provided
        if (dto.getFirstName() != null && dto.getLastName() != null) {
            user.setName(dto.getFirstName() + " " + dto.getLastName());
        }

        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }
}