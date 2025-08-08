package com.example.mvp.repository;


import com.example.mvp.database_table.AvailabilitySlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AvailabilitySlotRepository extends JpaRepository<AvailabilitySlot, Long> {

    List<AvailabilitySlot> findByTutorIdAndIsAvailableTrue(Long tutorId);

    @Query("SELECT a FROM AvailabilitySlot a WHERE a.tutor.id = :tutorId AND a.isAvailable = true AND a.startTime >= :startTime AND a.endTime <= :endTime")
    List<AvailabilitySlot> findAvailableSlotsByTutorAndTimeRange(@Param("tutorId") Long tutorId,
                                                                 @Param("startTime") LocalDateTime startTime,
                                                                 @Param("endTime") LocalDateTime endTime);

    @Query("SELECT a FROM AvailabilitySlot a WHERE a.tutor.id = :tutorId AND a.isAvailable = true AND a.startTime >= :now ORDER BY a.startTime")
    List<AvailabilitySlot> findUpcomingAvailableSlotsByTutor(@Param("tutorId") Long tutorId, @Param("now") LocalDateTime now);

    List<AvailabilitySlot> findByTutorId(Long tutorId);

    @Query("SELECT a FROM AvailabilitySlot a WHERE a.isAvailable = true AND a.startTime > :now ORDER BY a.startTime")
    List<AvailabilitySlot> findAllAvailableSlots(@Param("now") LocalDateTime now);
}
