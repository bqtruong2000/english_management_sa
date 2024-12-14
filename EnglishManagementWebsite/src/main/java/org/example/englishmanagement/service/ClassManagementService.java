package org.example.englishmanagement.service;

import org.example.englishmanagement.model.ClassManagement;
import org.example.englishmanagement.repository.ClassManagementRepository;
import org.example.englishmanagement.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.englishmanagement.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClassManagementService {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ClassManagementRepository classManagementRepository;


    public List<ClassManagement> getAllClassManagement() {
        return classManagementRepository.findAll();
    }

    public ClassManagement createClassManagement(ClassManagement classManagement) {
        // Validate existence of courseId
        if (!courseRepository.existsById(classManagement.getCourseId())) {
            throw new IllegalArgumentException("Invalid courseId: Course does not exist.");
        }

        for (String classId : classManagement.getClassIds()) {
            if (!classRepository.existsById(classId)) {
                throw new IllegalArgumentException("Invalid class id: Class does not exist for ID: " + classId);
            }
        }

        classManagement.setStatus(true);
        return classManagementRepository.save(classManagement);
    }


    public ClassManagement getClassManagementById(String courseId) {
        return classManagementRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found!"));
    }

    public ClassManagement updateClassManagement(ClassManagement updatedClass) {
        Optional<ClassManagement> existingCourseOpt = classManagementRepository.findById(updatedClass.getCourseId());
        if (existingCourseOpt.isEmpty()) {
            throw new RuntimeException("Course not found!");
        }

        ClassManagement existingCourse = existingCourseOpt.get();
        existingCourse.setClassIds(updatedClass.getClassIds());
        existingCourse.setStatus(updatedClass.isStatus());
        return classManagementRepository.save(existingCourse);
    }

    public void addClassToCourse(String courseId, String classId) {
        ClassManagement course = classManagementRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found!"));

        if (!course.getClassIds().contains(classId)) {
            course.getClassIds().add(classId);
        }
        classManagementRepository.save(course);
    }

    public void removeClassFromCourse(String courseId, String classId) {
        ClassManagement course = classManagementRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found!"));

        course.getClassIds().remove(classId);
        classManagementRepository.save(course);
    }

    public void hideCourse(String courseId) {
        ClassManagement course = classManagementRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found!"));

        course.setStatus(false);
        classManagementRepository.save(course);
    }

    public void exposeCourse(String courseId) {
        ClassManagement course = classManagementRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found!"));

        course.setStatus(true);
        classManagementRepository.save(course);
    }


}


