package com.example.mvp.controller;

import com.example.mvp.database_table.User;
import com.example.mvp.enums.UserRole;
import com.example.mvp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    // ADD THIS - Inject UserRepository
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/fix-user-roles")
    public ResponseEntity<?> fixUserRoles() {
        try {
            System.out.println("🔧 FIXING USER ROLES");

            // Find User ID 1 and make them a tutor if they're currently a student
            User user1 = userRepository.findById(1L).orElse(null);
            if (user1 != null) {
                System.out.println("Found user 1: " + user1.getName() + " with role: " + user1.getRole());

                if (user1.getRole() != UserRole.TUTOR) {
                    System.out.println("Changing user 1 role from " + user1.getRole() + " to TUTOR");
                    user1.setRole(UserRole.TUTOR);
                    userRepository.save(user1);
                    System.out.println("✅ User 1 role fixed to TUTOR");
                } else {
                    System.out.println("User 1 already has TUTOR role");
                }
            }

            return ResponseEntity.ok("✅ User roles checked and fixed! User 1 is now a TUTOR. Try booking again!");

        } catch (Exception e) {
            System.err.println("❌ Error fixing user roles: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/debug/roles")
    public ResponseEntity<?> debugUserRoles() {
        try {
            List<User> allUsers = userRepository.findAll();

            Map<String, Object> debug = new HashMap<>();
            debug.put("totalUsers", allUsers.size());

            List<Map<String, Object>> usersList = new ArrayList<>();
            for (User user : allUsers) {
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("id", user.getId());
                userInfo.put("name", user.getName());
                userInfo.put("email", user.getEmail());
                userInfo.put("role", user.getRole().toString());
                usersList.add(userInfo);
            }

            debug.put("users", usersList);
            return ResponseEntity.ok(debug);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(List.of());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}