package com.example.mvp.controller;

import com.example.mvp.database_table.SavedTutor;
import com.example.mvp.dto.SaveTutorDto;
import com.example.mvp.service.SavedTutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// SavedTutorController
@RestController
@RequestMapping("/api/saved-tutors")
@CrossOrigin(origins = "*")
public class SavedTutorController {

    @Autowired
    private SavedTutorService savedTutorService;

    @PostMapping("/save")
    public ResponseEntity<?> saveTutor(@RequestBody SaveTutorDto dto) {
        try {
            SavedTutor savedTutor = savedTutorService.saveTutor(dto.getTuteeId(), dto.getTutorId());
            return ResponseEntity.ok(savedTutor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tutee/{tuteeId}")
    public ResponseEntity<List<SavedTutor>> getSavedTutors(@PathVariable Long tuteeId) {
        return ResponseEntity.ok(savedTutorService.getSavedTutors(tuteeId));
    }

    @DeleteMapping("/remove/{tuteeId}/{tutorId}")
    public ResponseEntity<?> removeSavedTutor(@PathVariable Long tuteeId, @PathVariable Long tutorId) {
        try {
            savedTutorService.removeSavedTutor(tuteeId, tutorId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}