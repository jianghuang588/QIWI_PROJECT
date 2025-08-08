package com.example.mvp.controller;

import com.example.mvp.database_table.User;
import com.example.mvp.enums.UserRole;
import com.example.mvp.service.TuteeService;
import com.example.mvp.service.TutorService;
import com.example.mvp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Admin Controller for basic analytics
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private TuteeService tuteeService;

    @GetMapping("/stats")
    public Map<String, Object> getBasicStats() {
        Map<String, Object> stats = new HashMap<>();

        List<User> allUsers = userService.getAllUsers();
        List<User> tutors = userService.getUsersByRole(UserRole.TUTOR);
        List<User> tutees = userService.getUsersByRole(UserRole.TUTEE);

        stats.put("totalUsers", allUsers.size());
        stats.put("totalTutors", tutors.size());
        stats.put("totalTutees", tutees.size());
        stats.put("registrationDate", java.time.LocalDate.now().toString());

        // Role distribution
        Map<String, Integer> roleDistribution = new HashMap<>();
        roleDistribution.put("tutors", tutors.size());
        roleDistribution.put("tutees", tutees.size());
        stats.put("roleDistribution", roleDistribution);

        // Major distribution
        Map<String, Long> majorDistribution = allUsers.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        user -> user.getMajor().name(),
                        java.util.stream.Collectors.counting()
                ));
        stats.put("majorDistribution", majorDistribution);

        return stats;
    }
}
