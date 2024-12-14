package org.example.englishmanagement.service;

import org.example.englishmanagement.model.Class ;
import org.example.englishmanagement.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    public List<Class> getAllClasses() {
        return classRepository.findAll();
    }

    // Create Class Information
    public Class createClassInformation(Class classInfo) {
        return classRepository.save(classInfo);
    }

    // Update Class Information
    public Class updateClassInformation(String classId, Class updatedClass) {
        Optional<Class> existingClassOpt = classRepository.findById(classId);
        if (existingClassOpt.isEmpty()) {
            throw new RuntimeException("Class not found!");
        }

        Class existingClass = existingClassOpt.get();
        existingClass.setClassName(updatedClass.getClassName());
        existingClass.setRoom(updatedClass.getRoom());
        existingClass.setDuration(updatedClass.getDuration());
        existingClass.setIdTeacher(updatedClass.getIdTeacher());
        existingClass.setListStudent(updatedClass.getListStudent());

        return classRepository.save(existingClass);
    }

    // View Class Information
    public Class viewClassInformation(String classId) {
        return classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found!"));
    }

    // Attendance Record
    public String attendanceRecord(String classId, String studentId) {
        Class existingClass = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found!"));

        if (!existingClass.getListStudent().contains(studentId)) {
            throw new RuntimeException("Student is not part of this class!");
        }

        return "Attendance recorded for student: " + studentId;
    }

    // Get List of Students
    public List<String> getListStudent(String classId) {
        Class existingClass = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found!"));
        return existingClass.getListStudent();
    }

    // Check Attendance
    public boolean checkAttendance(String classId, String studentId) {
        Class existingClass = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found!"));
        return existingClass.getListStudent().contains(studentId);
    }
}
