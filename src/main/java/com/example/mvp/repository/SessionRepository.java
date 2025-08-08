package com.example.mvp.repository;

import com.example.mvp.database_table.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    // Basic queries using method names - these should work reliably
    List<Session> findByTutorId(Long tutorId);
    List<Session> findByStudentId(Long studentId);
    List<Session> findByStatus(Session.SessionStatus status);

    // FIXED: Use native queries for better reliability with enum types
    @Query(value = "SELECT * FROM sessions WHERE tutor_id = :tutorId AND status = 'PENDING' ORDER BY start_time ASC", nativeQuery = true)
    List<Session> findPendingSessionsByTutor(@Param("tutorId") Long tutorId);

    @Query(value = "SELECT * FROM sessions WHERE student_id = :studentId AND status = 'PENDING' ORDER BY start_time ASC", nativeQuery = true)
    List<Session> findPendingSessionsByStudent(@Param("studentId") Long studentId);

    // Upcoming sessions with better enum handling
    @Query(value = "SELECT * FROM sessions WHERE tutor_id = :tutorId AND start_time >= :now AND status IN ('PENDING', 'CONFIRMED') ORDER BY start_time ASC", nativeQuery = true)
    List<Session> findUpcomingSessionsByTutor(@Param("tutorId") Long tutorId, @Param("now") LocalDateTime now);

    @Query(value = "SELECT * FROM sessions WHERE student_id = :studentId AND start_time >= :now AND status IN ('PENDING', 'CONFIRMED') ORDER BY start_time ASC", nativeQuery = true)
    List<Session> findUpcomingSessionsByStudent(@Param("studentId") Long studentId, @Param("now") LocalDateTime now);

    // Time range conflicts - EXISTING METHOD (KEEP THIS)
    @Query("SELECT s FROM Session s WHERE s.tutor.id = :tutorId AND s.startTime <= :endTime AND s.endTime >= :startTime")
    List<Session> findSessionsByTutorAndTimeRange(@Param("tutorId") Long tutorId,
                                                  @Param("startTime") LocalDateTime startTime,
                                                  @Param("endTime") LocalDateTime endTime);

    // NEW: MISSING METHOD FOR STUDENT CONFLICTS
    @Query("SELECT s FROM Session s WHERE s.student.id = :studentId AND s.startTime <= :endTime AND s.endTime >= :startTime")
    List<Session> findSessionsByStudentAndTimeRange(@Param("studentId") Long studentId,
                                                    @Param("startTime") LocalDateTime startTime,
                                                    @Param("endTime") LocalDateTime endTime);

    // Debug queries
    @Query(value = "SELECT * FROM sessions", nativeQuery = true)
    List<Session> findAllNative();

    @Query(value = "SELECT * FROM sessions WHERE status = 'PENDING'", nativeQuery = true)
    List<Session> findAllPendingNative();

    @Query(value = "SELECT * FROM sessions WHERE tutor_id = :tutorId", nativeQuery = true)
    List<Session> findAllByTutorIdNative(@Param("tutorId") Long tutorId);

    // NEW: Comprehensive debug query
    @Query(value = "SELECT s.*, t.name as tutor_name, st.name as student_name FROM sessions s " +
            "LEFT JOIN users t ON s.tutor_id = t.id " +
            "LEFT JOIN users st ON s.student_id = st.id " +
            "WHERE s.tutor_id = :tutorId", nativeQuery = true)
    List<Object[]> findDebugSessionsByTutor(@Param("tutorId") Long tutorId);

    // WITH this native query:
    @Query(value = "SELECT * FROM sessions WHERE tutor_id = ?1 AND status = 'PENDING' ORDER BY start_time", nativeQuery = true)
    List<Session> findByTutorIdAndStatusOrderByStartTime(Long tutorId, Session.SessionStatus status);

    // OR better yet, add this new method and use it instead:
    @Query(value = "SELECT * FROM sessions WHERE tutor_id = ?1 AND status = 'PENDING' ORDER BY start_time", nativeQuery = true)
    List<Session> findPendingByTutorIdFixed(Long tutorId);
}