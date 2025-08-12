package com.example.mvp.database_table;

import com.example.mvp.enums.Subject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "tutors")
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User is required")
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "tutor_subjects", joinColumns = @JoinColumn(name = "tutor_id"))
    @Column(name = "subject", length = 50)  // ADD THIS LENGTH
    private List<Subject> subjects;

    @ElementCollection
    @CollectionTable(name = "tutor_grades", joinColumns = @JoinColumn(name = "tutor_id"))
    @MapKeyColumn(name = "subject")
    @Column(name = "grade")
    private Map<String, String> grades;

    @NotBlank(message = "Teaching approach is required")
    @Column(name = "teaching_approach", nullable = false, length = 500)
    private String teachingApproach;

    // Constructors
    public Tutor() {}

    public Tutor(User user, List<Subject> subjects, Map<String, String> grades, String teachingApproach) {
        this.user = user;
        this.subjects = subjects;
        this.grades = grades;
        this.teachingApproach = teachingApproach;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Subject> getSubjects() { return subjects; }
    public void setSubjects(List<Subject> subjects) { this.subjects = subjects; }

    public Map<String, String> getGrades() { return grades; }
    public void setGrades(Map<String, String> grades) { this.grades = grades; }

    public String getTeachingApproach() { return teachingApproach; }
    public void setTeachingApproach(String teachingApproach) { this.teachingApproach = teachingApproach; }
}