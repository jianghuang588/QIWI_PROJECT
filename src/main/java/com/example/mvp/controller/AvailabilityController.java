package com.example.mvp.controller;

import com.example.mvp.database_table.AvailabilitySlot;
import com.example.mvp.dto.AvailabilitySlotDto;
import com.example.mvp.service.AvailabilityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/availability")
@CrossOrigin(origins = "*")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @PostMapping
    public ResponseEntity<?> createAvailabilitySlot(@Valid @RequestBody AvailabilitySlotDto dto) {
        try {
            AvailabilitySlot slot = availabilityService.createAvailabilitySlot(dto);
            return ResponseEntity.ok(slot);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<AvailabilitySlot>> getTutorAvailableSlots(@PathVariable Long tutorId) {
        return ResponseEntity.ok(availabilityService.getTutorAvailableSlots(tutorId));
    }

    @GetMapping("/tutor/{tutorId}/all")
    public ResponseEntity<List<AvailabilitySlot>> getAllTutorSlots(@PathVariable Long tutorId) {
        return ResponseEntity.ok(availabilityService.getAllTutorSlots(tutorId));
    }

    @PutMapping("/{slotId}/availability")
    public ResponseEntity<?> updateSlotAvailability(@PathVariable Long slotId, @RequestParam boolean isAvailable) {
        try {
            AvailabilitySlot slot = availabilityService.updateSlotAvailability(slotId, isAvailable);
            return ResponseEntity.ok(slot);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{slotId}")
    public ResponseEntity<?> deleteAvailabilitySlot(@PathVariable Long slotId) {
        try {
            availabilityService.deleteAvailabilitySlot(slotId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ADD THIS METHOD:
    @GetMapping("/all-available")
    public ResponseEntity<List<AvailabilitySlot>> getAllAvailableSlots() {
        try {
            List<AvailabilitySlot> slots = availabilityService.getAllAvailableSlots();
            return ResponseEntity.ok(slots);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(List.of());
        }
    }


}