package org.example.englishmanagement.controller;

import org.example.englishmanagement.model.Course;
import org.example.englishmanagement.service.CourseService;
import org.example.englishmanagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/available")
    public ResponseEntity<List<Course>> getAvailableCourses() {
        return ResponseEntity.ok(courseService.getAvailableCourses());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@RequestBody Course course, @RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token); // Ensure only admin can add
        return ResponseEntity.ok(courseService.addCourse(course));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCourse(@RequestBody Course course, @RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token); // Ensure only admin can update
        return ResponseEntity.ok(courseService.updateCourse(course));
    }

    @GetMapping("/get/{courseId}")
    public ResponseEntity<?> getCourse(@PathVariable String courseId, @RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token); // Ensure only admin can view
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }

    @DeleteMapping("/delete/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable String courseId, @RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token); // Ensure only admin can delete
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok("Course deleted successfully!");
    }

    @PutMapping("/deactivate/{courseId}")
    public ResponseEntity<?> deactivateCourse(@PathVariable String courseId, @RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token);
        courseService.deactivateCourse(courseId);
        return ResponseEntity.ok(Collections.singletonMap("message", "Course deactivated successfully!"));
    }

    @PutMapping("/activate/{courseId}")
    public ResponseEntity<?> activateCourse(@PathVariable String courseId, @RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token);
        courseService.activateCourse(courseId);
        return ResponseEntity.ok(Collections.singletonMap("message", "Course activated successfully!"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses(@RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token); // Ensure admin access
        return ResponseEntity.ok(courseService.getAllCourses());
    }
}
