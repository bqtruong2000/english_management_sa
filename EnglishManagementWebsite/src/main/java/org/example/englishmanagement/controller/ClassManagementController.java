package org.example.englishmanagement.controller;

import org.example.englishmanagement.model.ClassManagement;
import org.example.englishmanagement.service.ClassManagementService;
import org.example.englishmanagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/class-management")
public class ClassManagementController {

    @Autowired
    private ClassManagementService classManagementService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllClassManagement(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(classManagementService.getAllClassManagement());
    }


    @PostMapping("/create")
    public ResponseEntity<?> createClassManagement(
            @RequestBody ClassManagement classManagement,
            @RequestHeader("Authorization") String token) {

        JwtUtil.validateAdminAccess(token); // Validate admin access

        try {
            ClassManagement createdClassManagement = classManagementService.createClassManagement(classManagement);
            return ResponseEntity.ok(createdClassManagement);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/get/{courseId}")
    public ResponseEntity<?> getClassManagement(@PathVariable String courseId, @RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token);
        return ResponseEntity.ok(classManagementService.getClassManagementById(courseId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateClassManagement(@RequestBody ClassManagement classManagement, @RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token);
        return ResponseEntity.ok(classManagementService.updateClassManagement(classManagement));
    }

    @PutMapping("/add-class/{courseId}/{classId}")
    public ResponseEntity<?> addClassToCourse(@PathVariable String courseId, @PathVariable String classId, @RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token);
        classManagementService.addClassToCourse(courseId, classId);
        return ResponseEntity.ok(Collections.singletonMap("message", "Class added successfully"));
    }

    @PutMapping("/remove-class/{courseId}/{classId}")
    public ResponseEntity<?> removeClassFromCourse(@PathVariable String courseId, @PathVariable String classId, @RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token);
        classManagementService.removeClassFromCourse(courseId, classId);
        return ResponseEntity.ok(Collections.singletonMap("message", "Class removed successfully"));
    }

    @PutMapping("/hide/{courseId}")
    public ResponseEntity<Map<String, String>> hideCourse(@PathVariable String courseId, @RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token);
        classManagementService.hideCourse(courseId);

        // Return a JSON response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Course successfully hidden!");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/expose/{courseId}")
    public ResponseEntity<Map<String, String>> exposeCourse(@PathVariable String courseId, @RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token);
        classManagementService.exposeCourse(courseId);

        // Return a JSON response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Course has been successfully activated.");
        return ResponseEntity.ok(response);
    }
}
