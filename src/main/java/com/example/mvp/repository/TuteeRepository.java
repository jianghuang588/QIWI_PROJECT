package com.example.mvp.repository;

import com.example.mvp.database_table.Tutee;
import com.example.mvp.enums.Major;
import com.example.mvp.enums.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// TuteeRepository
@Repository
public interface TuteeRepository extends JpaRepository<Tutee, Long> {
    Optional<Tutee> findByUserId(Long userId);
    List<Tutee> findByUserMajor(Major major);

    @Query("SELECT t FROM Tutee t JOIN t.subjectsNeeded s WHERE s = :subject")
    List<Tutee> findBySubjectNeeded(@Param("subject") Subject subject);
}
