package com.example.mvp.controller;

import com.example.mvp.database_table.Tutor;
import com.example.mvp.dto.TutorProfileDto;
import com.example.mvp.dto.TutorResponseDto;
import com.example.mvp.enums.Major;
import com.example.mvp.enums.Subject;
import com.example.mvp.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TutorController
@RestController
@RequestMapping("/api/tutors")
@CrossOrigin(origins = "*")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @PostMapping
    public ResponseEntity<?> createOrUpdateTutorProfile(@Valid @RequestBody TutorProfileDto dto) {
        try {
            Tutor tutor = tutorService.createOrUpdateTutorProfile(dto);
            return ResponseEntity.ok(tutor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateTutorProfile(@PathVariable Long userId, @Valid @RequestBody TutorProfileDto dto) {
        try {
            dto.setUserId(userId); // Ensure the userId matches
            Tutor tutor = tutorService.createOrUpdateTutorProfile(dto);
            return ResponseEntity.ok(tutor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<TutorResponseDto>> getAllTutors() {
        return ResponseEntity.ok(tutorService.getAllTutors());
    }

    @GetMapping("/major/{major}")
    public ResponseEntity<List<TutorResponseDto>> getTutorsByMajor(@PathVariable Major major) {
        return ResponseEntity.ok(tutorService.getTutorsByMajor(major));
    }

    @GetMapping("/subject/{subject}")
    public ResponseEntity<List<TutorResponseDto>> getTutorsBySubject(@PathVariable Subject subject) {
        return ResponseEntity.ok(tutorService.getTutorsBySubject(subject));
    }

    @GetMapping("/major/{major}/subject/{subject}")
    public ResponseEntity<List<TutorResponseDto>> getTutorsByMajorAndSubject(
            @PathVariable Major major, @PathVariable Subject subject) {
        return ResponseEntity.ok(tutorService.getTutorsByMajorAndSubject(major, subject));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTutorByUserId(@PathVariable Long userId) {
        try {
            Tutor tutor = tutorService.getTutorByUserId(userId);
            return ResponseEntity.ok(tutor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}