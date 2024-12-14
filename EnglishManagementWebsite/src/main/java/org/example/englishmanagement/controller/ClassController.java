package org.example.englishmanagement.controller;

import org.example.englishmanagement.model.Class;
import org.example.englishmanagement.service.ClassService;
import org.example.englishmanagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping("/get-all")
    public ResponseEntity<List<Class>> getAllClasses(@RequestHeader("Authorization") String token) {
        List<Class> classes = classService.getAllClasses();
        return ResponseEntity.ok(classes);
    }

    // Create Class
    @PostMapping("/create")
    public ResponseEntity<?> createClass(@RequestBody Class classInfo, @RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token);
        return ResponseEntity.ok(classService.createClassInformation(classInfo));
    }

    // Update Class
    @PutMapping("/update/{classId}")
    public ResponseEntity<?> updateClass(@PathVariable String classId, @RequestBody Class updatedClass, @RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token);
        return ResponseEntity.ok(classService.updateClassInformation(classId, updatedClass));
    }

    // View Class Information
    @GetMapping("/view/{classId}")
    public ResponseEntity<?> viewClass(@PathVariable String classId, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(classService.viewClassInformation(classId));
    }

    // Record Attendance
    @PostMapping("/attendance/{classId}/{studentId}")
    public ResponseEntity<?> recordAttendance(@PathVariable String classId, @PathVariable String studentId, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(classService.attendanceRecord(classId, studentId));
    }

    // Get List of Students
    @GetMapping("/students/{classId}")
    public ResponseEntity<?> getListStudent(@PathVariable String classId, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(classService.getListStudent(classId));
    }

    // Check Attendance
    @GetMapping("/attendance/{classId}/{studentId}")
    public ResponseEntity<?> checkAttendance(@PathVariable String classId, @PathVariable String studentId, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(classService.checkAttendance(classId, studentId));
    }
}
