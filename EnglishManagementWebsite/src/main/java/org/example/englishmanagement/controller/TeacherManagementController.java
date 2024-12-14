package org.example.englishmanagement.controller;

import org.example.englishmanagement.model.TeacherManagement;
import org.example.englishmanagement.service.TeacherManagementService;
import org.example.englishmanagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/teacher-management")
public class TeacherManagementController {

    @Autowired
    private TeacherManagementService teacherManagementService;

    @PostMapping("/create/{idTeacher}")
    public ResponseEntity<?> createTeacher(@PathVariable String idTeacher, @RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token);

        TeacherManagement createdTeacher = teacherManagementService.createTeacher(idTeacher);
        return ResponseEntity.ok(createdTeacher);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getTeachers(@RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token);
        return ResponseEntity.ok(teacherManagementService.getTeachers());
    }

    @GetMapping("/get/{idTeacher}")
    public ResponseEntity<?> getTeacherById(@PathVariable String idTeacher, @RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token);
        Optional<TeacherManagement> teacher = teacherManagementService.getTeacherById(idTeacher);
        return teacher.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add-to-class/{idTeacher}/{classId}")
    public ResponseEntity<?> addTeacherToClass(
            @PathVariable String idTeacher,
            @PathVariable String classId,
            @RequestHeader("Authorization") String token
    ) {
        JwtUtil.validateAdminAccess(token);
        teacherManagementService.addTeacherToClass(idTeacher, classId);
        return ResponseEntity.ok("Teacher assigned to class successfully!");
    }
}
