package com.example.mvp.service;

import com.example.mvp.database_table.Tutee;
import com.example.mvp.database_table.User;
import com.example.mvp.dto.TuteeProfileDto;
import com.example.mvp.enums.Major;
import com.example.mvp.enums.UserRole;
import com.example.mvp.repository.TuteeRepository;
import com.example.mvp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// TuteeService
@Service
@Transactional
public class TuteeService {

    @Autowired
    private TuteeRepository tuteeRepository;

    @Autowired
    private UserRepository userRepository;

    public Tutee createOrUpdateTuteeProfile(TuteeProfileDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != UserRole.TUTEE) {
            throw new RuntimeException("User is not a tutee");
        }

        // Check if tutee profile already exists
        Optional<Tutee> existingTutee = tuteeRepository.findByUserId(dto.getUserId());

        if (existingTutee.isPresent()) {
            // Update existing profile
            Tutee tutee = existingTutee.get();
            tutee.setSubjectsNeeded(dto.getSubjectsNeeded());
            tutee.setPersonalIntro(dto.getPersonalIntro());
            return tuteeRepository.save(tutee);
        } else {
            // Create new profile
            Tutee tutee = new Tutee(user, dto.getSubjectsNeeded(), dto.getPersonalIntro());
            return tuteeRepository.save(tutee);
        }
    }

    // Keep the old method for backward compatibility
    @Deprecated
    public Tutee createTuteeProfile(TuteeProfileDto dto) {
        return createOrUpdateTuteeProfile(dto);
    }

    public List<Tutee> getAllTutees() {
        return tuteeRepository.findAll();
    }

    public List<Tutee> getTuteesByMajor(Major major) {
        return tuteeRepository.findByUserMajor(major);
    }

    public Tutee getTuteeByUserId(Long userId) {
        return tuteeRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Tutee profile not found for user"));
    }
}