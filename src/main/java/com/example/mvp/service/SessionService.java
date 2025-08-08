package com.example.mvp.service;

import com.example.mvp.database_table.Session;
import com.example.mvp.database_table.User;
import com.example.mvp.dto.SessionBookingDto;
import com.example.mvp.dto.SessionUpdateDto;
import com.example.mvp.repository.SessionRepository;
import com.example.mvp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    // CORRECTED: Book session with proper conflict handling
    public Session bookSession(SessionBookingDto dto) {
        System.out.println("🚀 BOOKING SESSION - Tutor: " + dto.getTutorId() + ", Student: " + dto.getStudentId());

        User tutor = userRepository.findById(dto.getTutorId())
                .orElseThrow(() -> new RuntimeException("Tutor not found"));
        User student = userRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Validate that tutor is actually a TUTOR
        if (!tutor.getRole().toString().equals("TUTOR")) {
            throw new RuntimeException("User ID " + dto.getTutorId() + " is not a tutor!");
        }

        // Check for real conflicts (excluding this new session)
        String conflictWarning = checkForRealConflicts(dto.getStudentId(), dto.getTutorId(),
                dto.getStartTime(), dto.getEndTime(), null);

        Session session = new Session();
        session.setTutor(tutor);
        session.setStudent(student);
        session.setSubject(dto.getSubject());
        session.setStartTime(dto.getStartTime());
        session.setEndTime(dto.getEndTime());
        session.setStudentNotes(dto.getStudentNotes());
        session.setLocation(dto.getLocation() != null ? dto.getLocation() : "To be discussed");
        session.setStatus(Session.SessionStatus.PENDING);

        // Only add conflict warning if there's a real conflict
        if (!conflictWarning.isEmpty()) {
            session.setSessionNotes("⚠️ CONFLICT: " + conflictWarning);
            System.out.println("⚠️ CONFLICT DETECTED: " + conflictWarning);
        }

        Session saved = sessionRepository.save(session);
        System.out.println("✅ SESSION SAVED: ID=" + saved.getId() + ", STATUS=" + saved.getStatus());

        return saved;
    }

    // CORRECTED: Proper conflict checking that excludes the session being checked
    private String checkForRealConflicts(Long studentId, Long tutorId, LocalDateTime startTime,
                                         LocalDateTime endTime, Long excludeSessionId) {
        System.out.println("🔍 CHECKING REAL CONFLICTS - Student:" + studentId + " Tutor:" + tutorId +
                " (excluding session: " + excludeSessionId + ")");

        try {
            // Check student conflicts (excluding current session)
            List<Session> studentSessions = sessionRepository.findByStudentId(studentId);
            for (Session s : studentSessions) {
                // Skip the session we're checking against itself
                if (excludeSessionId != null && s.getId().equals(excludeSessionId)) {
                    continue;
                }

                if ((s.getStatus() == Session.SessionStatus.CONFIRMED || s.getStatus() == Session.SessionStatus.PENDING) &&
                        timesOverlap(s.getStartTime(), s.getEndTime(), startTime, endTime)) {
                    return "Student has another " + s.getSubject() + " session at " + s.getStartTime();
                }
            }

            // Check tutor conflicts (excluding current session)
            List<Session> tutorSessions = sessionRepository.findByTutorId(tutorId);
            for (Session s : tutorSessions) {
                // Skip the session we're checking against itself
                if (excludeSessionId != null && s.getId().equals(excludeSessionId)) {
                    continue;
                }

                if ((s.getStatus() == Session.SessionStatus.CONFIRMED || s.getStatus() == Session.SessionStatus.PENDING) &&
                        timesOverlap(s.getStartTime(), s.getEndTime(), startTime, endTime)) {
                    return "Tutor has another " + s.getSubject() + " session at " + s.getStartTime();
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error checking conflicts: " + e.getMessage());
        }

        return ""; // No conflicts
    }

    // CORRECTED: Public conflict checking method for API (excludes current session)
    public String checkForConflicts(Long studentId, Long tutorId, LocalDateTime startTime,
                                    LocalDateTime endTime, Long excludeSessionId) {
        return checkForRealConflicts(studentId, tutorId, startTime, endTime, excludeSessionId);
    }

    // Simple helper method
    private boolean timesOverlap(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }

    // Session management methods
    public Session updateSession(SessionUpdateDto dto) {
        Session session = sessionRepository.findById(dto.getSessionId())
                .orElseThrow(() -> new RuntimeException("Session not found"));
        session.setStatus(dto.getStatus());
        if (dto.getSessionNotes() != null) session.setSessionNotes(dto.getSessionNotes());
        if (dto.getMeetingLink() != null) session.setMeetingLink(dto.getMeetingLink());
        if (dto.getLocation() != null) session.setLocation(dto.getLocation());
        return sessionRepository.save(session);
    }

    public Session confirmSession(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        System.out.println("✅ Confirming session " + sessionId);
        session.setStatus(Session.SessionStatus.CONFIRMED);
        Session saved = sessionRepository.save(session);
        System.out.println("✅ Session confirmed with status: " + saved.getStatus());
        return saved;
    }

    public Session cancelSession(Long sessionId, String reason) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        System.out.println("❌ Cancelling session " + sessionId);
        session.setStatus(Session.SessionStatus.CANCELLED);
        if (reason != null && !reason.trim().isEmpty()) {
            session.setSessionNotes("Cancelled: " + reason);
        }
        Session saved = sessionRepository.save(session);
        System.out.println("❌ Session cancelled with status: " + saved.getStatus());
        return saved;
    }

    // Session retrieval methods
    public List<Session> getTutorSessions(Long tutorId) {
        System.out.println("📊 Getting all sessions for tutor " + tutorId);
        List<Session> sessions = sessionRepository.findByTutorId(tutorId);
        System.out.println("📊 Found " + sessions.size() + " total sessions");
        return sessions;
    }

    public List<Session> getStudentSessions(Long studentId) {
        System.out.println("📊 Getting all sessions for student " + studentId);
        List<Session> sessions = sessionRepository.findByStudentId(studentId);
        System.out.println("📊 Found " + sessions.size() + " total sessions");
        return sessions;
    }

    // CORRECTED: Tutor pending sessions (properly filtered)
    public List<Session> getTutorPendingSessions(Long tutorId) {
        System.out.println("🔍 Getting pending sessions for tutor: " + tutorId);

        // Verify this user is actually a tutor
        User user = userRepository.findById(tutorId).orElse(null);
        if (user == null) {
            System.out.println("❌ User " + tutorId + " not found");
            return List.of();
        }

        if (!user.getRole().toString().equals("TUTOR")) {
            System.out.println("❌ User " + tutorId + " is not a TUTOR, role is: " + user.getRole());
            return List.of();
        }

        System.out.println("✅ User " + tutorId + " (" + user.getName() + ") is confirmed TUTOR");

        // Get pending sessions for this tutor
        List<Session> allSessions = sessionRepository.findByTutorId(tutorId);
        List<Session> result = allSessions.stream()
                .filter(s -> s.getStatus() == Session.SessionStatus.PENDING)
                .sorted((a, b) -> a.getStartTime().compareTo(b.getStartTime()))
                .collect(Collectors.toList());

        System.out.println("✅ Found " + result.size() + " pending sessions for tutor " + tutorId);
        return result;
    }

    public List<Session> getUpcomingTutorSessions(Long tutorId) {
        try {
            List<Session> allSessions = sessionRepository.findByTutorId(tutorId);
            LocalDateTime now = LocalDateTime.now();

            return allSessions.stream()
                    .filter(s -> s.getStartTime().isAfter(now))
                    .filter(s -> s.getStatus() == Session.SessionStatus.CONFIRMED || s.getStatus() == Session.SessionStatus.PENDING)
                    .sorted((a, b) -> a.getStartTime().compareTo(b.getStartTime()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("❌ Error getting upcoming tutor sessions: " + e.getMessage());
            return List.of();
        }
    }

    public List<Session> getUpcomingStudentSessions(Long studentId) {
        try {
            List<Session> allSessions = sessionRepository.findByStudentId(studentId);
            LocalDateTime now = LocalDateTime.now();

            return allSessions.stream()
                    .filter(s -> s.getStartTime().isAfter(now))
                    .filter(s -> s.getStatus() == Session.SessionStatus.CONFIRMED || s.getStatus() == Session.SessionStatus.PENDING)
                    .sorted((a, b) -> a.getStartTime().compareTo(b.getStartTime()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("❌ Error getting upcoming student sessions: " + e.getMessage());
            return List.of();
        }
    }

    // Student-specific methods
    public List<Session> getStudentPendingSessions(Long studentId) {
        System.out.println("🔍 Getting pending sessions for student " + studentId);
        try {
            List<Session> allSessions = sessionRepository.findByStudentId(studentId);
            List<Session> pendingSessions = allSessions.stream()
                    .filter(s -> s.getStatus() == Session.SessionStatus.PENDING)
                    .filter(s -> s.getSessionNotes() == null || !s.getSessionNotes().contains("TUTOR_INITIATED"))
                    .sorted((a, b) -> a.getStartTime().compareTo(b.getStartTime()))
                    .collect(Collectors.toList());

            System.out.println("📊 Found " + pendingSessions.size() + " pending sessions for student");
            return pendingSessions;
        } catch (Exception e) {
            System.err.println("❌ Error getting student pending sessions: " + e.getMessage());
            return List.of();
        }
    }

    // Student pending offers (tutor-initiated sessions)
    public List<Session> getStudentPendingOffers(Long studentId) {
        System.out.println("🔍 Getting pending offers for student " + studentId);
        try {
            List<Session> allSessions = sessionRepository.findByStudentId(studentId);
            List<Session> pendingOffers = allSessions.stream()
                    .filter(s -> s.getStatus() == Session.SessionStatus.PENDING)
                    .filter(s -> s.getSessionNotes() != null && s.getSessionNotes().contains("TUTOR_INITIATED"))
                    .sorted((a, b) -> a.getStartTime().compareTo(b.getStartTime()))
                    .collect(Collectors.toList());

            System.out.println("📊 Found " + pendingOffers.size() + " pending offers for student");
            return pendingOffers;
        } catch (Exception e) {
            System.err.println("❌ Error getting student pending offers: " + e.getMessage());
            return List.of();
        }
    }

    // Accept/decline tutor offers
    public Session acceptTutorOffer(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        System.out.println("✅ Student accepting tutor offer for session " + sessionId);
        session.setStatus(Session.SessionStatus.CONFIRMED);
        Session saved = sessionRepository.save(session);
        System.out.println("✅ Tutor offer accepted with status: " + saved.getStatus());
        return saved;
    }

    public Session declineTutorOffer(Long sessionId, String reason) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        System.out.println("❌ Student declining tutor offer for session " + sessionId);
        session.setStatus(Session.SessionStatus.CANCELLED);
        if (reason != null && !reason.trim().isEmpty()) {
            session.setSessionNotes("Student declined: " + reason);
        }
        Session saved = sessionRepository.save(session);
        System.out.println("❌ Tutor offer declined with status: " + saved.getStatus());
        return saved;
    }

    // Utility methods
    public Session getSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
    }

    public List<Session> getAllSessions() {
        List<Session> allSessions = sessionRepository.findAll();
        System.out.println("📊 === ALL SESSIONS IN DATABASE ===");
        System.out.println("📊 Total count: " + allSessions.size());

        for (Session session : allSessions) {
            System.out.println("   📄 Session " + session.getId() + ":");
            System.out.println("      - Status: " + session.getStatus());
            System.out.println("      - Subject: " + session.getSubject());
            System.out.println("      - Tutor: " + session.getTutor().getId() + " (" + session.getTutor().getName() + ") [" + session.getTutor().getRole() + "]");
            System.out.println("      - Student: " + session.getStudent().getId() + " (" + session.getStudent().getName() + ") [" + session.getStudent().getRole() + "]");
            System.out.println("      - Time: " + session.getStartTime());
        }

        return allSessions;
    }
}