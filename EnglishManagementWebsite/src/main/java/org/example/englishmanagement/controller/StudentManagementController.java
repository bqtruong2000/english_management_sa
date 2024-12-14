package org.example.englishmanagement.controller;

import org.example.englishmanagement.model.StudentManagement;
import org.example.englishmanagement.service.StudentManagementService;
import org.example.englishmanagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/student-management")
public class StudentManagementController {

    @Autowired
    private StudentManagementService studentManagementService;

    @PostMapping("/create/{idStudent}")
    public ResponseEntity<?> createStudent(@PathVariable String idStudent, @RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token); // Ensure only admin can create

        StudentManagement createdStudent = studentManagementService.createStudent(idStudent);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getStudents(@RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token);
        return ResponseEntity.ok(studentManagementService.getStudents());
    }

    @GetMapping("/get/{idStudent}")
    public ResponseEntity<?> getStudentById(@PathVariable String idStudent, @RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token);
        Optional<StudentManagement> student = studentManagementService.getStudentById(idStudent);
        return student.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add-to-class/{idStudent}/{classId}")
    public ResponseEntity<?> addStudentToClass(
            @PathVariable String idStudent,
            @PathVariable String classId,
            @RequestHeader("Authorization") String token
    ) {
        JwtUtil.validateAdminAccess(token);
        studentManagementService.addStudentToClass(idStudent, classId);
        return ResponseEntity.ok("Student added to class successfully!");
    }

    @PutMapping("/approve-waiting/{studentId}/{classId}")
    public ResponseEntity<?> approveWaitingListStudent(
            @PathVariable String studentId,
            @PathVariable String classId,
            @RequestHeader("Authorization") String token
    ) {
        String adminId = JwtUtil.extractCode(token); // Admin ID from JWT
        studentManagementService.addWaitingListToClass(adminId, studentId, classId);
        return ResponseEntity.ok("Student has been added to the class from the waiting list!");
    }
}
