package com.example.mvp.database_table;

import com.example.mvp.enums.Subject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "tutees")
public class Tutee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User is required")
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "tutee_subjects", joinColumns = @JoinColumn(name = "tutee_id"))
    @Column(name = "subject")
    private List<Subject> subjectsNeeded;

    @NotBlank(message = "Personal introduction is required")
    @Column(name = "personal_intro", nullable = false, length = 500)
    private String personalIntro;

    // Constructors
    public Tutee() {}

    public Tutee(User user, List<Subject> subjectsNeeded, String personalIntro) {
        this.user = user;
        this.subjectsNeeded = subjectsNeeded;
        this.personalIntro = personalIntro;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Subject> getSubjectsNeeded() { return subjectsNeeded; }
    public void setSubjectsNeeded(List<Subject> subjectsNeeded) { this.subjectsNeeded = subjectsNeeded; }

    public String getPersonalIntro() { return personalIntro; }
    public void setPersonalIntro(String personalIntro) { this.personalIntro = personalIntro; }
}
