package com.example.mvp.repository;

import com.example.mvp.database_table.SavedTutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// SavedTutorRepository
@Repository
public interface SavedTutorRepository extends JpaRepository<SavedTutor, Long> {
    List<SavedTutor> findByTuteeId(Long tuteeId);
    boolean existsByTuteeIdAndTutorId(Long tuteeId, Long tutorId);
    void deleteByTuteeIdAndTutorId(Long tuteeId, Long tutorId);
}