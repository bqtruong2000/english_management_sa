package org.example.englishmanagement.controller;

import org.example.englishmanagement.service.TeacherCommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.englishmanagement.util.JwtUtil;

@RestController
@RequestMapping("/teacher-communication")
public class TeacherCommunicationController {
    @Autowired
    private TeacherCommunicationService teacherCommunicationService;

    @PostMapping("/send-notification/{classId}")
    public ResponseEntity<?> sendNotification(@PathVariable String classId, @RequestBody String content) {
        teacherCommunicationService.sendNotification(classId, content);
        return ResponseEntity.ok("Notification sent successfully!");
    }

    @GetMapping("/get-score/{classId}/{studentId}")
    public ResponseEntity<?> getScore(@PathVariable String classId, @PathVariable String studentId) {
        return ResponseEntity.ok(teacherCommunicationService.getScore(classId, studentId));
    }

    @PutMapping("/edit-score/{classId}/{studentId}")
    public ResponseEntity<?> editScore(
            @PathVariable String classId,
            @PathVariable String studentId,
            @RequestParam float midterm,
            @RequestParam float finalExam
    ) {
        teacherCommunicationService.editScore(classId, studentId, midterm, finalExam);
        return ResponseEntity.ok("Score updated successfully!");
    }

    @PostMapping("/create-score/{classId}/{studentId}")
    public ResponseEntity<?> createScore(
            @PathVariable String classId,
            @PathVariable String studentId,
            @RequestHeader("Authorization") String token
    ) {
        JwtUtil.validateTeacherAccess(token); // Ensure only teachers can create scores
        teacherCommunicationService.createScore(classId, studentId);
        return ResponseEntity.ok("Score created successfully for the student in the class!");
    }

}

