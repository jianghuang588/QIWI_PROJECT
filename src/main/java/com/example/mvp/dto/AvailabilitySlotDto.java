package com.example.mvp.dto;


import com.example.mvp.database_table.AvailabilitySlot;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AvailabilitySlotDto {
    @NotNull(message = "Tutor ID is required")
    private Long tutorId;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    private AvailabilitySlot.RecurringType recurringType;

    public AvailabilitySlotDto() {}

    // Getters and Setters
    public Long getTutorId() { return tutorId; }
    public void setTutorId(Long tutorId) { this.tutorId = tutorId; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public AvailabilitySlot.RecurringType getRecurringType() { return recurringType; }
    public void setRecurringType(AvailabilitySlot.RecurringType recurringType) { this.recurringType = recurringType; }
}