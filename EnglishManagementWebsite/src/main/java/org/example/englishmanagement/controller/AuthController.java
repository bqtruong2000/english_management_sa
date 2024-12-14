package org.example.englishmanagement.controller;

import org.example.englishmanagement.repository.AuthRepository;
import org.example.englishmanagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthFacade authFacade;

    @Autowired
    private StudentManagementService studentManagementService;

    @Autowired
    private TeacherManagementService teacherManagementService;

    @Autowired
    private AuthService authService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private AuthRepository authRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String userName,
                                           @RequestParam String password,
                                           @RequestParam String roleId) {
        try {
            String userId = authFacade.register(userName, password, roleId);

            if ("student".equalsIgnoreCase(roleId)) {
                studentManagementService.createStudent(userId);

            } else if ("teacher".equalsIgnoreCase(roleId)) {
                teacherManagementService.createTeacher(userId);
            }

            return ResponseEntity.ok("User registered successfully with ID: " + userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String userName,
                                                     @RequestParam String password) {
        Map<String, String> response = authFacade.login(userName, password);
        if (response.containsKey("message")) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-by-role/{roleId}")
    public ResponseEntity<?> getUsersByRole(@PathVariable String roleId) {
        try {
            return ResponseEntity.ok(authService.getUsersByRole(roleId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching users: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/profile-status")
    public ResponseEntity<?> checkProfileStatus(@PathVariable String id) {
        boolean profileExists = profileService.profileExists(id);
        return ResponseEntity.ok(Map.of("profileExists", profileExists));
    }

    @GetMapping("/count-by-role")
    public Map<String, Long> countByRoleId() {
        Map<String, Long> roleCounts = new HashMap<>();
        roleCounts.put("students", authRepository.countByRoleId("student"));
        roleCounts.put("teachers", authRepository.countByRoleId("teacher"));
        return roleCounts;
    }
}
