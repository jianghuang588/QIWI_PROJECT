package com.example.mvp.service;

import com.example.mvp.database_table.SavedTutor;
import com.example.mvp.database_table.User;
import com.example.mvp.repository.SavedTutorRepository;
import com.example.mvp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// SavedTutorService
@Service
@Transactional
public class SavedTutorService {

    @Autowired
    private SavedTutorRepository savedTutorRepository;

    @Autowired
    private UserRepository userRepository;

    public SavedTutor saveTutor(Long tuteeId, Long tutorId) {
        if (savedTutorRepository.existsByTuteeIdAndTutorId(tuteeId, tutorId)) {
            throw new RuntimeException("Tutor already saved");
        }

        User tutee = userRepository.findById(tuteeId)
                .orElseThrow(() -> new RuntimeException("Tutee not found"));

        User tutor = userRepository.findById(tutorId)
                .orElseThrow(() -> new RuntimeException("Tutor not found"));

        SavedTutor savedTutor = new SavedTutor(tutee, tutor);
        return savedTutorRepository.save(savedTutor);
    }

    public List<SavedTutor> getSavedTutors(Long tuteeId) {
        return savedTutorRepository.findByTuteeId(tuteeId);
    }

    public void removeSavedTutor(Long tuteeId, Long tutorId) {
        savedTutorRepository.deleteByTuteeIdAndTutorId(tuteeId, tutorId);
    }
}
