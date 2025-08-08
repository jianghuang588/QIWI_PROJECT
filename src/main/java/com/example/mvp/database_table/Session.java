package com.example.mvp.database_table;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Tutor is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tutor_id", nullable = false)
    private User tutor;

    @NotNull(message = "Student is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @NotNull(message = "Subject is required")
    @Column(nullable = false)
    private String subject;

    @NotNull(message = "Start time is required")
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)  // FIXED: Added length constraint
    private SessionStatus status;

    @Column(name = "session_notes", length = 1000)
    private String sessionNotes;

    @Column(name = "student_notes", length = 500)
    private String studentNotes;

    @Column(name = "location")
    private String location;

    @Column(name = "meeting_link")
    private String meetingLink;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        // CRITICAL FIX: Always ensure PENDING status
        if (status == null) {
            status = SessionStatus.PENDING;
        }

        System.out.println("🆕 @PrePersist - Creating session:");
        System.out.println("   Status: " + status);
        System.out.println("   Subject: " + subject);
        System.out.println("   Tutor: " + (tutor != null ? tutor.getName() + "(ID:" + tutor.getId() + ")" : "null"));
        System.out.println("   Student: " + (student != null ? student.getName() + "(ID:" + student.getId() + ")" : "null"));
        System.out.println("   Will be saved with status: " + status.name());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        System.out.println("📝 @PreUpdate - Updating session " + id + ":");
        System.out.println("   New Status: " + status);
    }

    @PostPersist
    protected void afterCreate() {
        System.out.println("✅ @PostPersist - Session " + id + " created successfully:");
        System.out.println("   Final Status: " + status);
        System.out.println("   Subject: " + subject);
        System.out.println("   Tutor ID: " + (tutor != null ? tutor.getId() : "null"));
        System.out.println("   Student ID: " + (student != null ? student.getId() : "null"));
    }

    // Constructors with DEFENSIVE status setting
    public Session() {
        this.status = SessionStatus.PENDING; // ALWAYS DEFAULT TO PENDING
        System.out.println("🏗️ Session() - Default constructor, status: " + this.status);
    }

    public Session(User tutor, User student, String subject, LocalDateTime startTime, LocalDateTime endTime, String studentNotes) {
        this.tutor = tutor;
        this.student = student;
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
        this.studentNotes = studentNotes;
        this.status = SessionStatus.PENDING; // ALWAYS DEFAULT TO PENDING
        System.out.println("🏗️ Session(params) - Constructor, status: " + this.status);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getTutor() { return tutor; }
    public void setTutor(User tutor) {
        System.out.println("📝 Setting tutor: " + (tutor != null ? tutor.getName() + " (ID: " + tutor.getId() + ")" : "null"));
        this.tutor = tutor;
    }

    public User getStudent() { return student; }
    public void setStudent(User student) {
        System.out.println("📝 Setting student: " + (student != null ? student.getName() + " (ID: " + student.getId() + ")" : "null"));
        this.student = student;
    }

    public String getSubject() { return subject; }
    public void setSubject(String subject) {
        System.out.println("📝 Setting subject: " + subject);
        this.subject = subject;
    }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) {
        System.out.println("📝 Setting start time: " + startTime);
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) {
        System.out.println("📝 Setting end time: " + endTime);
        this.endTime = endTime;
    }

    public SessionStatus getStatus() { return status; }
    public void setStatus(SessionStatus status) {
        System.out.println("📝 Changing session " + (id != null ? id : "NEW") + " status from " + this.status + " to " + status);
        this.status = status;
    }

    public String getSessionNotes() { return sessionNotes; }
    public void setSessionNotes(String sessionNotes) { this.sessionNotes = sessionNotes; }

    public String getStudentNotes() { return studentNotes; }
    public void setStudentNotes(String studentNotes) {
        this.studentNotes = studentNotes;
    }

    public String getLocation() { return location; }
    public void setLocation(String location) {
        System.out.println("📝 Setting location: " + location);
        this.location = location;
    }

    public String getMeetingLink() { return meetingLink; }
    public void setMeetingLink(String meetingLink) { this.meetingLink = meetingLink; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // FIXED: Added method to ensure status is never null
    public void ensureValidStatus() {
        if (this.status == null) {
            this.status = SessionStatus.PENDING;
            System.out.println("⚠️ Status was null, defaulted to PENDING");
        }
    }

    public enum SessionStatus {
        PENDING, CONFIRMED, CANCELLED, COMPLETED
    }
}