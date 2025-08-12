package com.example.mvp.database_table;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "classes")
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Class name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Class code is required")
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotBlank(message = "Professor name is required")
    @Column(name = "professor", nullable = false)
    private String professor;

    @NotBlank(message = "Year is required")
    @Column(name = "year", nullable = false)
    private String year;

    @NotBlank(message = "Semester is required")
    @Column(name = "semester", nullable = false)
    private String semester;

    @NotNull(message = "ECTS is required")
    @Column(name = "ects", nullable = false)
    private Integer ects;

    // Constructors, getters, setters
    public Class() {}

    // Add all getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getProfessor() { return professor; }
    public void setProfessor(String professor) { this.professor = professor; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public Integer getEcts() { return ects; }
    public void setEcts(Integer ects) { this.ects = ects; }
}
