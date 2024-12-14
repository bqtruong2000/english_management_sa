package org.example.englishmanagement.service;

import org.example.englishmanagement.model.TeacherManagement;
import org.example.englishmanagement.repository.AuthRepository;
import org.example.englishmanagement.repository.ClassRepository;
import org.example.englishmanagement.repository.TeacherManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.englishmanagement.model.Class;

import java.util.Optional;

@Service
public class TeacherManagementService {

    @Autowired
    private TeacherManagementRepository teacherManagementRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private AuthRepository authRepository;

    public TeacherManagement createTeacher(String idTeacher) {
        if(teacherManagementRepository.findById(idTeacher).isPresent()) {
            throw new RuntimeException("Teacher already exists.");
        }

        TeacherManagement teacher = new TeacherManagement();
        teacher.setIdTeacher(idTeacher);
        return teacherManagementRepository.save(teacher);
    }

    // Get all teachers
    public Iterable<TeacherManagement> getTeachers() {
        return teacherManagementRepository.findAll();
    }

    // Get a teacher by ID
    public Optional<TeacherManagement> getTeacherById(String idTeacher) {
        return teacherManagementRepository.findByIdTeacher(idTeacher);
    }

    @Transactional
    public void addTeacherToClass(String idTeacher, String classId) {
        // Verify the teacher exists
        TeacherManagement teacher = teacherManagementRepository.findByIdTeacher(idTeacher)
                .orElseThrow(() -> new RuntimeException("Teacher not found!"));

        // Verify the class exists
        org.example.englishmanagement.model.Class targetClass = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found!"));

        // Check if the teacher is already assigned
        if (targetClass.getIdTeacher() != null && targetClass.getIdTeacher().equals(idTeacher)) {
            throw new RuntimeException("Teacher is already assigned to this class!");
        }

        // Assign the teacher to the class
        targetClass.setIdTeacher(idTeacher);
        classRepository.save(targetClass);

        // Update the teacher's classId
        teacher.setClassId(classId);
        teacherManagementRepository.save(teacher);
    }
}
