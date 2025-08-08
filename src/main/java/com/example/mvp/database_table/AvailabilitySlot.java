package com.example.mvp.database_table;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "availability_slots")
public class AvailabilitySlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Tutor is required")
    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private User tutor;

    @NotNull(message = "Start time is required")
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;

    @Column(name = "recurring_type")
    @Enumerated(EnumType.STRING)
    private RecurringType recurringType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Constructors
    public AvailabilitySlot() {}

    public AvailabilitySlot(User tutor, LocalDateTime startTime, LocalDateTime endTime) {
        this.tutor = tutor;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = true;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getTutor() { return tutor; }
    public void setTutor(User tutor) { this.tutor = tutor; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }

    public RecurringType getRecurringType() { return recurringType; }
    public void setRecurringType(RecurringType recurringType) { this.recurringType = recurringType; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public enum RecurringType {
        NONE, WEEKLY, MONTHLY
    }
}
