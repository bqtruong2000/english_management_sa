package org.example.englishmanagement.service;

import org.example.englishmanagement.model.ClassRegistration;
import org.example.englishmanagement.model.StudentManagement;
import org.example.englishmanagement.repository.AuthRepository;
import org.example.englishmanagement.repository.ClassRegistrationRepository;
import org.example.englishmanagement.repository.ClassRepository;
import org.example.englishmanagement.repository.StudentManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.englishmanagement.util.JwtUtil;

import java.util.List;
import java.util.Optional;

@Service
public class StudentManagementService {

    @Autowired
    private StudentManagementRepository studentManagementRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ClassRegistrationRepository classRegistrationRepository;

    public StudentManagement createStudent(String idStudent) {
        if (studentManagementRepository.findById(idStudent).isPresent()) {
            throw new RuntimeException("Student already exists.");
        }

        StudentManagement student = new StudentManagement();
        student.setIdStudent(idStudent);
        student.setClassIds(List.of()); // Initialize with an empty list
        return studentManagementRepository.save(student);
    }

    // Get all students
    public Iterable<StudentManagement> getStudents() {
        return studentManagementRepository.findAll();
    }

    // Get a student by ID
    public Optional<StudentManagement> getStudentById(String idStudent) {
        return studentManagementRepository.findByIdStudent(idStudent);
    }

    // Add student to a class
    @Transactional
    public void addStudentToClass(String idStudent, String classId) {
        // Verify the student exists
        StudentManagement student = studentManagementRepository.findByIdStudent(idStudent)
                .orElseThrow(() -> new RuntimeException("Student not found!"));

        // Verify the class exists
        org.example.englishmanagement.model.Class targetClass = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found!"));

        // Add student ID to the class's listStudent
        if (targetClass.getListStudent().contains(idStudent)) {
            throw new RuntimeException("Student already in the class!");
        }

        targetClass.getListStudent().add(idStudent);
        classRepository.save(targetClass);

        // Update student's classIds
        if (!student.getClassIds().contains(classId)) {
            student.getClassIds().add(classId);
            studentManagementRepository.save(student);
        }
    }

    // Add a student from the waiting list to a class
    @Transactional
    public void addWaitingListToClass(String adminId, String studentId, String classId) {
        // Verify the student is in the waiting list for the class
        ClassRegistration registration = classRegistrationRepository.findByStudentId(studentId)
                .stream()
                .filter(r -> r.getClassId().equals(classId) && !r.isApproved())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Student not in waiting list for this class!"));

        // Approve the student
        registration.setApproved(true);
        classRegistrationRepository.save(registration);

        // Add the student to the class
        org.example.englishmanagement.model.Class targetClass = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found!"));

        if (targetClass.getListStudent().contains(studentId)) {
            throw new RuntimeException("Student already added to the class!");
        }

        targetClass.getListStudent().add(studentId);
        classRepository.save(targetClass);

        // Update student's classIds
        StudentManagement student = studentManagementRepository.findByIdStudent(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found!"));
        if (!student.getClassIds().contains(classId)) {
            student.getClassIds().add(classId);
            studentManagementRepository.save(student);
        }
    }
}