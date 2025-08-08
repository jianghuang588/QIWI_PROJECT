package com.example.mvp.service;

import com.example.mvp.database_table.AvailabilitySlot;
import com.example.mvp.database_table.User;
import com.example.mvp.dto.AvailabilitySlotDto;
import com.example.mvp.repository.AvailabilitySlotRepository;
import com.example.mvp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AvailabilityService {

    @Autowired
    private AvailabilitySlotRepository availabilityRepository;

    @Autowired
    private UserRepository userRepository;

    public AvailabilitySlot createAvailabilitySlot(AvailabilitySlotDto dto) {
        User tutor = userRepository.findById(dto.getTutorId())
                .orElseThrow(() -> new RuntimeException("Tutor not found"));

        AvailabilitySlot slot = new AvailabilitySlot(tutor, dto.getStartTime(), dto.getEndTime());
        slot.setRecurringType(dto.getRecurringType());

        return availabilityRepository.save(slot);
    }

    public List<AvailabilitySlot> getTutorAvailableSlots(Long tutorId) {
        return availabilityRepository.findUpcomingAvailableSlotsByTutor(tutorId, LocalDateTime.now());
    }

    public List<AvailabilitySlot> getAllTutorSlots(Long tutorId) {
        return availabilityRepository.findByTutorId(tutorId);
    }

    public AvailabilitySlot updateSlotAvailability(Long slotId, boolean isAvailable) {
        AvailabilitySlot slot = availabilityRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Availability slot not found"));

        slot.setIsAvailable(isAvailable);
        return availabilityRepository.save(slot);
    }

    public void deleteAvailabilitySlot(Long slotId) {
        availabilityRepository.deleteById(slotId);
    }

    public List<AvailabilitySlot> getAvailableSlotsByTimeRange(Long tutorId, LocalDateTime startTime, LocalDateTime endTime) {
        return availabilityRepository.findAvailableSlotsByTutorAndTimeRange(tutorId, startTime, endTime);
    }

    // ADD THESE TWO METHODS:
    public List<AvailabilitySlot> getAllAvailableSlots() {
        return availabilityRepository.findAllAvailableSlots(LocalDateTime.now());
    }

    public void markSlotAsTaken(Long slotId) {
        AvailabilitySlot slot = availabilityRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Availability slot not found"));

        slot.setIsAvailable(false);
        availabilityRepository.save(slot);
    }
}