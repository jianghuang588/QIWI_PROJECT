package com.example.mvp.database_table;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

// SavedTutor Entity
@Entity
@Table(name = "saved_tutors")
public class SavedTutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Tutee is required")
    @ManyToOne
    @JoinColumn(name = "tutee_id", nullable = false)
    private User tutee;

    @NotNull(message = "Tutor is required")
    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private User tutor;

    @Column(name = "saved_at", nullable = false)
    private LocalDateTime savedAt;

    @PrePersist
    protected void onCreate() {
        savedAt = LocalDateTime.now();
    }

    // Constructors
    public SavedTutor() {}

    public SavedTutor(User tutee, User tutor) {
        this.tutee = tutee;
        this.tutor = tutor;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getTutee() { return tutee; }
    public void setTutee(User tutee) { this.tutee = tutee; }

    public User getTutor() { return tutor; }
    public void setTutor(User tutor) { this.tutor = tutor; }

    public LocalDateTime getSavedAt() { return savedAt; }
    public void setSavedAt(LocalDateTime savedAt) { this.savedAt = savedAt; }
}
