package com.example.mvp.controller;

import com.example.mvp.database_table.Tutee;
import com.example.mvp.dto.TuteeProfileDto;
import com.example.mvp.enums.Major;
import com.example.mvp.service.TuteeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TuteeController
@RestController
@RequestMapping("/api/tutees")
@CrossOrigin(origins = "*")
public class TuteeController {

    @Autowired
    private TuteeService tuteeService;

    @PostMapping
    public ResponseEntity<?> createOrUpdateTuteeProfile(@Valid @RequestBody TuteeProfileDto dto) {
        try {
            Tutee tutee = tuteeService.createOrUpdateTuteeProfile(dto);
            return ResponseEntity.ok(tutee);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateTuteeProfile(@PathVariable Long userId, @Valid @RequestBody TuteeProfileDto dto) {
        try {
            dto.setUserId(userId); // Ensure the userId matches
            Tutee tutee = tuteeService.createOrUpdateTuteeProfile(dto);
            return ResponseEntity.ok(tutee);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Tutee>> getAllTutees() {
        return ResponseEntity.ok(tuteeService.getAllTutees());
    }

    @GetMapping("/major/{major}")
    public ResponseEntity<List<Tutee>> getTuteesByMajor(@PathVariable Major major) {
        return ResponseEntity.ok(tuteeService.getTuteesByMajor(major));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTuteeByUserId(@PathVariable Long userId) {
        try {
            Tutee tutee = tuteeService.getTuteeByUserId(userId);
            return ResponseEntity.ok(tutee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}