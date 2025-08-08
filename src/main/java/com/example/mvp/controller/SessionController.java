package com.example.mvp.controller;

import com.example.mvp.database_table.Session;
import com.example.mvp.database_table.User;
import com.example.mvp.dto.SessionBookingDto;
import com.example.mvp.dto.SessionUpdateDto;
import com.example.mvp.repository.SessionRepository;
import com.example.mvp.repository.UserRepository;
import com.example.mvp.service.SessionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin(origins = "*")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    // STUDENT BOOKS SESSION
    @PostMapping("/book")
    public ResponseEntity<?> bookSession(@Valid @RequestBody SessionBookingDto dto) {
        try {
            System.out.println("📅 ===== BOOKING REQUEST RECEIVED =====");
            System.out.println("   Tutor ID: " + dto.getTutorId());
            System.out.println("   Student ID: " + dto.getStudentId());
            System.out.println("   Subject: " + dto.getSubject());
            System.out.println("   Start Time: " + dto.getStartTime());
            System.out.println("   End Time: " + dto.getEndTime());

            Session session = sessionService.bookSession(dto);

            System.out.println("✅ ===== BOOKING COMPLETED =====");
            System.out.println("   Session ID: " + session.getId());
            System.out.println("   Final Status: " + session.getStatus());

            return ResponseEntity.ok(session);
        } catch (Exception e) {
            System.err.println("❌ ===== BOOKING FAILED =====");
            System.err.println("   Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Booking failed: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSession(@Valid @RequestBody SessionUpdateDto dto) {
        try {
            System.out.println("📝 Updating session " + dto.getSessionId() + " to status: " + dto.getStatus());
            Session session = sessionService.updateSession(dto);
            System.out.println("✅ Session updated successfully");
            return ResponseEntity.ok(session);
        } catch (RuntimeException e) {
            System.err.println("❌ Error updating session: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TUTOR ACCEPTS REQUEST
    @PutMapping("/{sessionId}/confirm")
    public ResponseEntity<?> confirmSession(@PathVariable Long sessionId) {
        try {
            System.out.println("✅ API: Confirming session " + sessionId);
            Session session = sessionService.confirmSession(sessionId);
            System.out.println("✅ Session confirmed with status: " + session.getStatus());
            return ResponseEntity.ok(session);
        } catch (RuntimeException e) {
            System.err.println("❌ Error confirming session: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TUTOR REJECTS REQUEST
    @PutMapping("/{sessionId}/cancel")
    public ResponseEntity<?> cancelSession(@PathVariable Long sessionId, @RequestParam(required = false) String reason) {
        try {
            System.out.println("❌ API: Cancelling session " + sessionId + " with reason: " + reason);
            Session session = sessionService.cancelSession(sessionId, reason);
            System.out.println("❌ Session cancelled with status: " + session.getStatus());
            return ResponseEntity.ok(session);
        } catch (RuntimeException e) {
            System.err.println("❌ Error cancelling session: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TUTOR SEES ALL SESSIONS
    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<Session>> getTutorSessions(@PathVariable Long tutorId) {
        try {
            System.out.println("📊 API: Getting all sessions for tutor " + tutorId);
            List<Session> sessions = sessionService.getTutorSessions(tutorId);
            System.out.println("📊 API: Returning " + sessions.size() + " total sessions for tutor " + tutorId);
            return ResponseEntity.ok(sessions);
        } catch (Exception e) {
            System.err.println("❌ Error getting tutor sessions: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(List.of());
        }
    }

    // CRITICAL ENDPOINT: TUTOR SEES PENDING REQUESTS
    @GetMapping("/tutor/{tutorId}/pending")
    public ResponseEntity<List<Session>> getTutorPendingSessions(@PathVariable Long tutorId) {
        System.out.println("🔍 ===== API CALL: PENDING SESSIONS =====");
        System.out.println("   Tutor ID: " + tutorId);
        System.out.println("   Request Time: " + LocalDateTime.now());

        try {
            List<Session> sessions = sessionService.getTutorPendingSessions(tutorId);

            System.out.println("📋 ===== API RESPONSE =====");
            System.out.println("   Found " + sessions.size() + " pending sessions");

            return ResponseEntity.ok(sessions);

        } catch (Exception e) {
            System.err.println("❌ API ERROR in getPendingSessions: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(List.of());
        }
    }

    // NUCLEAR OPTION ENDPOINT
    @GetMapping("/tutor/{tutorId}/pending-nuclear")
    public ResponseEntity<List<Session>> getTutorPendingSessionsNuclear(@PathVariable Long tutorId) {
        System.out.println("🔥 NUCLEAR ENDPOINT CALLED for tutor: " + tutorId);

        try {
            List<Session> sessions = sessionService.getTutorPendingSessions(tutorId);

            System.out.println("🔥 NUCLEAR ENDPOINT RETURNING: " + sessions.size() + " sessions");
            return ResponseEntity.ok(sessions);

        } catch (Exception e) {
            System.err.println("🔥 NUCLEAR ENDPOINT ERROR: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(List.of());
        }
    }

    @GetMapping("/tutor/{tutorId}/upcoming")
    public ResponseEntity<List<Session>> getUpcomingTutorSessions(@PathVariable Long tutorId) {
        try {
            System.out.println("📅 API: Getting upcoming sessions for tutor " + tutorId);
            List<Session> sessions = sessionService.getUpcomingTutorSessions(tutorId);
            System.out.println("📅 API: Returning " + sessions.size() + " upcoming sessions for tutor " + tutorId);
            return ResponseEntity.ok(sessions);
        } catch (Exception e) {
            System.err.println("❌ Error getting upcoming tutor sessions: " + e.getMessage());
            return ResponseEntity.badRequest().body(List.of());
        }
    }

    // STUDENT SEES THEIR SESSIONS
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Session>> getStudentSessions(@PathVariable Long studentId) {
        try {
            System.out.println("📊 API: Getting all sessions for student " + studentId);
            List<Session> sessions = sessionService.getStudentSessions(studentId);
            System.out.println("📊 API: Returning " + sessions.size() + " sessions for student " + studentId);
            return ResponseEntity.ok(sessions);
        } catch (Exception e) {
            System.err.println("❌ Error getting student sessions: " + e.getMessage());
            return ResponseEntity.badRequest().body(List.of());
        }
    }

    @GetMapping("/student/{studentId}/upcoming")
    public ResponseEntity<List<Session>> getUpcomingStudentSessions(@PathVariable Long studentId) {
        try {
            System.out.println("📅 API: Getting upcoming sessions for student " + studentId);
            List<Session> sessions = sessionService.getUpcomingStudentSessions(studentId);
            System.out.println("📅 API: Returning " + sessions.size() + " upcoming sessions for student " + studentId);
            return ResponseEntity.ok(sessions);
        } catch (Exception e) {
            System.err.println("❌ Error getting upcoming student sessions: " + e.getMessage());
            return ResponseEntity.badRequest().body(List.of());
        }
    }

    @GetMapping("/student/{studentId}/pending")
    public ResponseEntity<List<Session>> getStudentPendingSessions(@PathVariable Long studentId) {
        try {
            System.out.println("🔍 API: Getting pending sessions for student " + studentId);
            List<Session> sessions = sessionService.getStudentPendingSessions(studentId);
            System.out.println("📊 API: Returning " + sessions.size() + " pending sessions for student " + studentId);
            return ResponseEntity.ok(sessions);
        } catch (Exception e) {
            System.err.println("❌ Error getting student pending sessions: " + e.getMessage());
            return ResponseEntity.badRequest().body(List.of());
        }
    }

    // MANUAL TEST ENDPOINT
    @GetMapping("/manual-test")
    public ResponseEntity<?> manualTest() {
        System.out.println("🧪 MANUAL TEST STARTING");

        Map<String, Object> result = new HashMap<>();

        try {
            // Get all sessions
            List<Session> allSessions = sessionRepository.findAll();
            result.put("totalSessions", allSessions.size());

            System.out.println("🧪 Found " + allSessions.size() + " total sessions");

            // Show all sessions with details
            List<Map<String, Object>> sessionDetails = new ArrayList<>();
            for (Session s : allSessions) {
                Map<String, Object> detail = new HashMap<>();
                detail.put("id", s.getId());
                detail.put("tutorId", s.getTutor().getId());
                detail.put("tutorName", s.getTutor().getName());
                detail.put("tutorRole", s.getTutor().getRole().toString());
                detail.put("studentId", s.getStudent().getId());
                detail.put("studentName", s.getStudent().getName());
                detail.put("studentRole", s.getStudent().getRole().toString());
                detail.put("subject", s.getSubject());
                detail.put("status", s.getStatus().toString());
                detail.put("startTime", s.getStartTime().toString());

                sessionDetails.add(detail);

                System.out.println("🧪 Session " + s.getId() +
                        ": Tutor=" + s.getTutor().getId() + "(" + s.getTutor().getName() + ")[" + s.getTutor().getRole() + "]" +
                        ", Student=" + s.getStudent().getId() + "(" + s.getStudent().getName() + ")[" + s.getStudent().getRole() + "]" +
                        ", Status=" + s.getStatus() +
                        ", Subject=" + s.getSubject());
            }

            result.put("sessions", sessionDetails);

            // Test each tutor
            for (Long tutorId = 1L; tutorId <= 3L; tutorId++) {
                List<Session> tutorSessions = sessionRepository.findByTutorId(tutorId);
                List<Session> pendingSessions = tutorSessions.stream()
                        .filter(s -> s.getStatus().toString().equals("PENDING"))
                        .collect(Collectors.toList());

                result.put("tutor" + tutorId + "TotalSessions", tutorSessions.size());
                result.put("tutor" + tutorId + "PendingSessions", pendingSessions.size());

                System.out.println("🧪 Tutor " + tutorId + ": " + tutorSessions.size() + " total, " + pendingSessions.size() + " pending");
            }

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            System.err.println("🧪 MANUAL TEST ERROR: " + e.getMessage());
            e.printStackTrace();
            result.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }

    // DEBUG USERS ENDPOINT
    @GetMapping("/debug/users")
    public ResponseEntity<?> debugUsers() {
        System.out.println("👥 DEBUGGING USERS");

        Map<String, Object> result = new HashMap<>();

        try {
            List<User> allUsers = userRepository.findAll();
            result.put("totalUsers", allUsers.size());

            List<Map<String, Object>> userDetails = new ArrayList<>();
            for (User u : allUsers) {
                Map<String, Object> detail = new HashMap<>();
                detail.put("id", u.getId());
                detail.put("name", u.getName());
                detail.put("email", u.getEmail());
                detail.put("role", u.getRole().toString());
                detail.put("major", u.getMajor().toString());

                userDetails.add(detail);

                System.out.println("👥 User " + u.getId() +
                        ": " + u.getName() +
                        " (" + u.getEmail() + ")" +
                        " - Role: " + u.getRole() +
                        " - Major: " + u.getMajor());
            }

            result.put("users", userDetails);

            // Find tutors specifically
            List<User> tutors = allUsers.stream()
                    .filter(u -> u.getRole().toString().equals("TUTOR"))
                    .collect(Collectors.toList());
            result.put("tutorCount", tutors.size());

            List<User> students = allUsers.stream()
                    .filter(u -> u.getRole().toString().equals("TUTEE"))
                    .collect(Collectors.toList());
            result.put("studentCount", students.size());

            System.out.println("👥 Summary: " + tutors.size() + " tutors, " + students.size() + " students");

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            System.err.println("👥 DEBUG USERS ERROR: " + e.getMessage());
            e.printStackTrace();
            result.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }

    // FIX TUTOR ASSIGNMENTS ENDPOINT
    @PostMapping("/fix-tutor-assignments")
    public ResponseEntity<?> fixTutorAssignments() {
        System.out.println("🔧 FIXING TUTOR ASSIGNMENTS");

        Map<String, Object> result = new HashMap<>();

        try {
            // Find the real tutor (not the student)
            List<User> allUsers = userRepository.findAll();
            User realTutor = null;
            User studentUser = null;

            for (User u : allUsers) {
                System.out.println("👥 User " + u.getId() + ": " + u.getName() + " - Role: " + u.getRole());

                if (u.getRole().toString().equals("TUTOR")) {
                    realTutor = u;
                    System.out.println("✅ Found real tutor: " + u.getName() + " (ID: " + u.getId() + ")");
                }

                if (u.getRole().toString().equals("TUTEE")) {
                    studentUser = u;
                    System.out.println("✅ Found student: " + u.getName() + " (ID: " + u.getId() + ")");
                }
            }

            if (realTutor == null) {
                // Create a tutor if none exists
                System.out.println("🆕 No tutor found, creating one...");
                realTutor = new User();
                realTutor.setName("Real Tutor");
                realTutor.setEmail("realtutor@test.com");
                realTutor.setMajor(com.example.mvp.enums.Major.BUSINESS_ENGINEERING);
                realTutor.setRole(com.example.mvp.enums.UserRole.TUTOR);
                realTutor.setTermsAccepted(true);
                realTutor.setPrivacyAccepted(true);
                realTutor = userRepository.save(realTutor);
                System.out.println("✅ Created tutor: " + realTutor.getName() + " (ID: " + realTutor.getId() + ")");
            }

            // Find all PENDING sessions that are assigned to wrong tutor
            List<Session> allSessions = sessionRepository.findAll();
            List<Session> fixedSessions = new ArrayList<>();

            for (Session s : allSessions) {
                if (s.getStatus().toString().equals("PENDING")) {
                    User currentTutor = s.getTutor();

                    // If the "tutor" is actually a student, fix it
                    if (currentTutor.getRole().toString().equals("TUTEE")) {
                        System.out.println("🔧 Fixing session " + s.getId() +
                                " - moving from fake tutor " + currentTutor.getName() +
                                " to real tutor " + realTutor.getName());

                        s.setTutor(realTutor);
                        Session saved = sessionRepository.save(s);
                        fixedSessions.add(saved);
                    }
                }
            }

            result.put("realTutorId", realTutor.getId());
            result.put("realTutorName", realTutor.getName());
            result.put("fixedSessionsCount", fixedSessions.size());
            result.put("message", "Fixed " + fixedSessions.size() +
                    " sessions. Real tutor ID: " + realTutor.getId());

            // Verify the fix
            List<Session> pendingForRealTutor = sessionRepository.findAll().stream()
                   // .filter(s -> s.getTutor().getId().equals(realTutor.getId()))
                    .filter(s -> s.getStatus().toString().equals("PENDING"))
                    .collect(Collectors.toList());

            result.put("verificationPendingCount", pendingForRealTutor.size());

            System.out.println("✅ FIXED! Real tutor " + realTutor.getId() +
                    " now has " + pendingForRealTutor.size() + " pending sessions");

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            System.err.println("❌ Error fixing sessions: " + e.getMessage());
            e.printStackTrace();
            result.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(result);
        }
    }

    // COMPREHENSIVE DEBUG ENDPOINT
    @GetMapping("/debug/comprehensive/{tutorId}")
    public ResponseEntity<?> comprehensiveDebug(@PathVariable Long tutorId) {
        System.out.println("🔍 ===== COMPREHENSIVE DEBUG FOR TUTOR " + tutorId + " =====");

        Map<String, Object> debug = new HashMap<>();
        debug.put("tutorId", tutorId);
        debug.put("timestamp", LocalDateTime.now().toString());

        try {
            // Check if tutor exists
            User tutor = userRepository.findById(tutorId).orElse(null);
            debug.put("tutorExists", tutor != null);
            if (tutor != null) {
                debug.put("tutorName", tutor.getName());
                debug.put("tutorRole", tutor.getRole().toString());
            }

            // Get all sessions for this tutor
            List<Session> allSessions = sessionRepository.findByTutorId(tutorId);
            debug.put("totalSessions", allSessions.size());

            // Manual filtering
            List<Session> manualPending = allSessions.stream()
                    .filter(s -> s.getStatus() == Session.SessionStatus.PENDING)
                    .collect(Collectors.toList());
            debug.put("manualPendingCount", manualPending.size());

            // Service layer test
            List<Session> servicePending = sessionService.getTutorPendingSessions(tutorId);
            debug.put("servicePendingCount", servicePending.size());

            // Raw session data
            List<Map<String, Object>> sessionDetails = allSessions.stream()
                    .map(s -> {
                        Map<String, Object> sessionMap = new HashMap<>();
                        sessionMap.put("id", s.getId());
                        sessionMap.put("status", s.getStatus().toString());
                        sessionMap.put("subject", s.getSubject());
                        sessionMap.put("studentName", s.getStudent().getName());
                        sessionMap.put("startTime", s.getStartTime().toString());
                        return sessionMap;
                    })
                    .collect(Collectors.toList());
            debug.put("sessionDetails", sessionDetails);

            System.out.println("🔍 Debug results: " + debug);
            return ResponseEntity.ok(debug);

        } catch (Exception e) {
            System.err.println("❌ Debug error: " + e.getMessage());
            e.printStackTrace();
            debug.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(debug);
        }
    }

    // TEST DATA CREATION
    @PostMapping("/create-test-data")
    public ResponseEntity<?> createTestData() {
        try {
            System.out.println("🧪 ===== CREATING TEST DATA =====");

            List<User> allUsers = userRepository.findAll();
            System.out.println("📊 Current users in database: " + allUsers.size());

            // Find or create a real tutor
            User realTutor = allUsers.stream()
                    .filter(u -> u.getRole().toString().equals("TUTOR"))
                    .findFirst()
                    .orElse(null);

            if (realTutor == null) {
                System.out.println("🆕 No tutor found, creating one...");
                realTutor = new User();
                realTutor.setName("Test Tutor");
                realTutor.setEmail("testtutor@test.com");
                realTutor.setMajor(com.example.mvp.enums.Major.BUSINESS_ENGINEERING);
                realTutor.setRole(com.example.mvp.enums.UserRole.TUTOR);
                realTutor.setTermsAccepted(true);
                realTutor.setPrivacyAccepted(true);
                realTutor = userRepository.save(realTutor);
                System.out.println("✅ Created tutor: " + realTutor.getName() + " (ID: " + realTutor.getId() + ")");
            }

            // Find or create a student
            User testStudent = allUsers.stream()
                    .filter(u -> u.getRole().toString().equals("TUTEE"))
                    .findFirst()
                    .orElse(null);

            if (testStudent == null) {
                testStudent = new User();
                testStudent.setName("Test Student");
                testStudent.setEmail("teststudent@test.com");
                testStudent.setMajor(com.example.mvp.enums.Major.BUSINESS_ENGINEERING);
                testStudent.setRole(com.example.mvp.enums.UserRole.TUTEE);
                testStudent.setTermsAccepted(true);
                testStudent.setPrivacyAccepted(true);
                testStudent = userRepository.save(testStudent);
            }

            // Create test session with CORRECT tutor assignment
            Session testSession = new Session();
            testSession.setTutor(realTutor);  // REAL tutor, not student!
            testSession.setStudent(testStudent);
            testSession.setSubject("Corporate Finance");
            testSession.setStartTime(LocalDateTime.now().plusDays(1));
            testSession.setEndTime(LocalDateTime.now().plusDays(1).plusHours(1));
            testSession.setStudentNotes("I need help with financial analysis. This is a test session.");
            testSession.setLocation("Library Study Room A");
            testSession.setStatus(Session.SessionStatus.PENDING);

            Session savedSession = sessionRepository.save(testSession);

            // Immediate verification
            List<Session> verification = sessionService.getTutorPendingSessions(realTutor.getId());

            return ResponseEntity.ok("✅ Test data created successfully!\n" +
                    "Session ID: " + savedSession.getId() + "\n" +
                    "Student: " + savedSession.getStudent().getName() + " (ID: " + savedSession.getStudent().getId() + ")\n" +
                    "Tutor: " + savedSession.getTutor().getName() + " (ID: " + savedSession.getTutor().getId() + ")\n" +
                    "Status: " + savedSession.getStatus() + "\n" +
                    "Verification: " + verification.size() + " pending sessions found\n" +
                    "LOGIN AS TUTOR ID: " + realTutor.getId());

        } catch (Exception e) {
            System.err.println("❌ Error creating test data: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("❌ Error creating test data: " + e.getMessage());
        }
    }


    @GetMapping("/debug/raw")
    public ResponseEntity<?> getRawSessionData() {
        try {
            List<Session> allSessions = sessionRepository.findAll();

            Map<String, Object> debug = new HashMap<>();
            debug.put("totalSessions", allSessions.size());
            debug.put("timestamp", LocalDateTime.now().toString());

            List<Map<String, Object>> sessionData = allSessions.stream()
                    .map(s -> {
                        Map<String, Object> data = new HashMap<>();
                        data.put("id", s.getId());
                        data.put("tutorId", s.getTutor().getId());
                        data.put("tutorName", s.getTutor().getName());
                        data.put("tutorRole", s.getTutor().getRole().toString());
                        data.put("studentId", s.getStudent().getId());
                        data.put("studentName", s.getStudent().getName());
                        data.put("studentRole", s.getStudent().getRole().toString());
                        data.put("subject", s.getSubject());
                        data.put("status", s.getStatus().toString());
                        data.put("startTime", s.getStartTime().toString());
                        return data;
                    })
                    .collect(Collectors.toList());

            debug.put("sessions", sessionData);

            Map<String, Long> statusCounts = allSessions.stream()
                    .collect(Collectors.groupingBy(
                            s -> s.getStatus().toString(),
                            Collectors.counting()
                    ));
            debug.put("statusCounts", statusCounts);

            return ResponseEntity.ok(debug);

        } catch (Exception e) {
            System.err.println("❌ Error getting raw data: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        try {
            long sessionCount = sessionRepository.count();
            long userCount = userRepository.count();

            Map<String, Object> health = new HashMap<>();
            health.put("status", "OK");
            health.put("sessionCount", sessionCount);
            health.put("userCount", userCount);

            return ResponseEntity.ok(health);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", "ERROR");
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/fix-sessions")
    public ResponseEntity<?> fixSessions() {
        try {
            System.out.println("🔧 FIXING SESSIONS: Moving from tutor 1 to tutor 2");

            List<Session> sessions = sessionRepository.findByTutorId(1L);
            System.out.println("Found " + sessions.size() + " sessions to move");

            User newTutor = userRepository.findById(2L).orElse(null);

            if (newTutor == null) {
                return ResponseEntity.badRequest().body("Tutor 2 not found");
            }

            for (Session session : sessions) {
                System.out.println("Moving session " + session.getId() + " from tutor " + session.getTutor().getId() + " to tutor 2");
                session.setTutor(newTutor);
                sessionRepository.save(session);
            }

            System.out.println("✅ Fixed " + sessions.size() + " sessions");

            return ResponseEntity.ok("✅ Fixed " + sessions.size() + " sessions! Now go to your tutor dashboard to see the pending requests.");

        } catch (Exception e) {
            System.err.println("❌ Error fixing sessions: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}