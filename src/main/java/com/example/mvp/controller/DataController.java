package com.example.mvp.controller;

import com.example.mvp.enums.Major;
import com.example.mvp.enums.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// DataController (for dropdowns)
@RestController
@RequestMapping("/api/data")
@CrossOrigin(origins = "*")
public class DataController {

    @GetMapping("/majors")
    public ResponseEntity<List<Major>> getMajors() {
        return ResponseEntity.ok(Arrays.asList(Major.values()));
    }

    @GetMapping("/subjects")
    public ResponseEntity<List<Subject>> getSubjects() {
        return ResponseEntity.ok(Arrays.asList(Subject.values()));
    }

    @GetMapping("/subjects/major/{major}")
    public ResponseEntity<List<Subject>> getSubjectsByMajor(@PathVariable Major major) {
        List<Subject> subjects = Arrays.stream(Subject.values())
                .filter(subject -> subject.getMajor() == major)
                .collect(Collectors.toList());
        return ResponseEntity.ok(subjects);
    }
}
