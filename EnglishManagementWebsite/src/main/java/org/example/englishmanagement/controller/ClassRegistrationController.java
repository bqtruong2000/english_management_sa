package org.example.englishmanagement.controller;

import org.example.englishmanagement.model.StudentManagement;
import org.example.englishmanagement.service.ClassRegistrationService;
import org.example.englishmanagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class-registration")
public class ClassRegistrationController {
    @Autowired
    private ClassRegistrationService classRegistrationService;

    /**
     * Get the list of students in the waiting list for a specific class.
     * @param classId The class ID.
     * @return List of students in the waiting list.
     */
    @GetMapping("/waiting-list/{classId}")
    public ResponseEntity<?> getStudentsInWaitingList(@PathVariable String classId) {
        List<StudentManagement> students = classRegistrationService.getStudentsInWaitingList(classId);
        return ResponseEntity.ok(students);
    }

    @PostMapping("/register/{classId}")
    public ResponseEntity<?> registerForClass(
            @RequestHeader("Authorization") String token,
            @PathVariable String classId
    ) {
        String studentId = JwtUtil.extractCode(token); // Student ID from JWT
        String response = classRegistrationService.registerForClass(studentId, classId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cancel/{classId}")
    public ResponseEntity<?> cancelRegistration(
            @RequestHeader("Authorization") String token,
            @PathVariable String classId
    ) {
        String studentId = JwtUtil.extractCode(token); // Student ID from JWT
        classRegistrationService.cancelClassRegistration(studentId, classId);
        return ResponseEntity.ok("Registration canceled successfully!");
    }

    @GetMapping("/get-registrations/{classId}")
    public ResponseEntity<?> getClassRegistrations(@PathVariable String classId) {
        return ResponseEntity.ok(classRegistrationService.getClassRegistrations(classId));
    }


}
