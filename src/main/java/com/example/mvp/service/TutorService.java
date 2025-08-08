package com.example.mvp.service;

import com.example.mvp.database_table.Tutor;
import com.example.mvp.database_table.User;
import com.example.mvp.dto.TutorProfileDto;
import com.example.mvp.dto.TutorResponseDto;
import com.example.mvp.enums.Major;
import com.example.mvp.enums.Subject;
import com.example.mvp.enums.UserRole;
import com.example.mvp.repository.TutorRepository;
import com.example.mvp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// TutorService
@Service
@Transactional
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private UserRepository userRepository;

    public Tutor createOrUpdateTutorProfile(TutorProfileDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != UserRole.TUTOR) {
            throw new RuntimeException("User is not a tutor");
        }

        // Check if tutor profile already exists
        Optional<Tutor> existingTutor = tutorRepository.findByUserId(dto.getUserId());

        if (existingTutor.isPresent()) {
            // Update existing profile
            Tutor tutor = existingTutor.get();
            tutor.setSubjects(dto.getSubjects());
            tutor.setGrades(dto.getGrades());
            tutor.setTeachingApproach(dto.getTeachingApproach());
            return tutorRepository.save(tutor);
        } else {
            // Create new profile
            Tutor tutor = new Tutor(user, dto.getSubjects(), dto.getGrades(), dto.getTeachingApproach());
            return tutorRepository.save(tutor);
        }
    }

    // Keep the old method for backward compatibility
    @Deprecated
    public Tutor createTutorProfile(TutorProfileDto dto) {
        return createOrUpdateTutorProfile(dto);
    }

    public List<TutorResponseDto> getAllTutors() {
        return tutorRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<TutorResponseDto> getTutorsByMajor(Major major) {
        return tutorRepository.findByUserMajor(major).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<TutorResponseDto> getTutorsBySubject(Subject subject) {
        return tutorRepository.findBySubject(subject).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<TutorResponseDto> getTutorsByMajorAndSubject(Major major, Subject subject) {
        return tutorRepository.findBySubjectAndMajor(subject, major).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    private TutorResponseDto convertToResponseDto(Tutor tutor) {
        TutorResponseDto dto = new TutorResponseDto();

        // ✅ CRITICAL FIX: Return User ID instead of Tutor ID
        dto.setId(tutor.getUser().getId());  // CHANGE THIS LINE

        dto.setName(tutor.getUser().getName());
        dto.setEmail(tutor.getUser().getEmail());
        dto.setMajor(tutor.getUser().getMajor());
        dto.setSubjects(tutor.getSubjects());
        dto.setGrades(tutor.getGrades());
        dto.setTeachingApproach(tutor.getTeachingApproach());
        return dto;
    }



    public Tutor getTutorByUserId(Long userId) {
        return tutorRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Tutor profile not found for user"));
    }
}