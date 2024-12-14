package org.example.englishmanagement.service;

import org.example.englishmanagement.model.Course;
import org.example.englishmanagement.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course addCourse(Course course) {
        course.setStatus(true); // Set status to active by default
        return courseRepository.save(course);
    }

    public Course updateCourse(Course updatedCourse) {
        Optional<Course> existingCourseOpt = courseRepository.findById(updatedCourse.getCourseId());
        if (existingCourseOpt.isEmpty()) {
            throw new RuntimeException("Course not found!");
        }

        Course existingCourse = existingCourseOpt.get();
        existingCourse.setName(updatedCourse.getName());
        existingCourse.setDescription(updatedCourse.getDescription());
        existingCourse.setStatus(updatedCourse.isStatus());
        return courseRepository.save(existingCourse);
    }

    public Course getCourseById(String courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found!"));
    }

    public void deleteCourse(String courseId) {
        Optional<Course> existingCourseOpt = courseRepository.findById(courseId);
        if (existingCourseOpt.isEmpty()) {
            throw new RuntimeException("Course not found!");
        }

        courseRepository.deleteById(courseId);
    }

    public void deactivateCourse(String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found!"));

        course.setStatus(false); // Set status to inactive
        courseRepository.save(course);
    }

    public void activateCourse(String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found!"));

        course.setStatus(true); // Set status to active
        courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getAvailableCourses() {
        return courseRepository.findByStatusTrue();
    }
}
