package com.example.mvp.repository;

import com.example.mvp.database_table.Tutor;
import com.example.mvp.enums.Major;
import com.example.mvp.enums.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// TutorRepository
@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    Optional<Tutor> findByUserId(Long userId);
    List<Tutor> findByUserMajor(Major major);

    @Query("SELECT t FROM Tutor t JOIN t.subjects s WHERE s = :subject")
    List<Tutor> findBySubject(@Param("subject") Subject subject);

    @Query("SELECT t FROM Tutor t JOIN t.subjects s WHERE s = :subject AND t.user.major = :major")
    List<Tutor> findBySubjectAndMajor(@Param("subject") Subject subject, @Param("major") Major major);
}
