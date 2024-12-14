package org.example.englishmanagement.service;

import org.example.englishmanagement.model.Class;
import org.example.englishmanagement.model.Timetable;
import org.example.englishmanagement.repository.ClassRepository;
import org.example.englishmanagement.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TimetableService {
    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private ClassRepository classRepository;

    public List<Map<String, String>> getClassesByDate(String date) {
        List<Timetable> timetables = timetableRepository.findAll();

        return timetables.stream()
                .filter(t -> t.getSchedule().stream().anyMatch(s -> s.getDate().equals(date)))
                .flatMap(t -> t.getSchedule().stream()
                        .filter(s -> s.getDate().equals(date))
                        .map(s -> Map.of(
                                "classId", t.getClassId(),
                                "startDate", s.getStartDate(),
                                "endDate", s.getEndDate()
                        )))
                .collect(Collectors.toList());
    }


    private void validateSchedule(List<Timetable.ScheduleItem> schedule) {
        for (int i = 0; i < schedule.size(); i++) {
            Timetable.ScheduleItem current = schedule.get(i);

            for (int j = i + 1; j < schedule.size(); j++) {
                Timetable.ScheduleItem other = schedule.get(j);

                if (current.getDate().equals(other.getDate())) {
                    LocalTime currentStart = current.getStartTime();
                    LocalTime currentEnd = current.getEndTime();
                    LocalTime otherStart = other.getStartTime();
                    LocalTime otherEnd = other.getEndTime();

                    // Check for overlap
                    if (!(currentEnd.isBefore(otherStart) || otherEnd.isBefore(currentStart))) {
                        throw new RuntimeException("Schedule items conflict: " + current + " and " + other);
                    }
                }
            }
        }
    }

    public Timetable createTimeTable(String classId, List<Timetable.ScheduleItem> schedule) {
        if (schedule.size() < 3) {
            throw new RuntimeException("A timetable must contain equal or more than 3 dates!");
        }
        if (timetableRepository.findByClassId(classId).isPresent()) {
            throw new RuntimeException("Timetable for this class already exists!");
        }

        validateSchedule(schedule);

        Timetable timetable = new Timetable();
        timetable.setClassId(classId);
        timetable.setSchedule(schedule);

        return timetableRepository.save(timetable);
    }

    public Timetable updateTimeTable(String classId, List<Timetable.ScheduleItem> newSchedule) {
        if (newSchedule.size() < 3) {
            throw new RuntimeException("A timetable must contain equal or more than 3 dates!");
        }

        Timetable existingTimetable = timetableRepository.findByClassId(classId)
                .orElseThrow(() -> new RuntimeException("Timetable not found!"));

        validateSchedule(newSchedule);

        existingTimetable.setSchedule(newSchedule);

        return timetableRepository.save(existingTimetable);
    }

    public Timetable getTimeTable(String classId) {
        return timetableRepository.findByClassId(classId)
                .orElseThrow(() -> new RuntimeException("Timetable not found!"));
    }

    public void deleteTimeTable(String classId) {
        Timetable timetable = timetableRepository.findByClassId(classId)
                .orElseThrow(() -> new RuntimeException("Timetable not found!"));
        timetableRepository.delete(timetable);
    }

    public List<Map<String, Object>> getStudentClassesAndTimetables(String studentId) {
        List<Class> classesForStudent = classRepository.findByListStudentContaining(studentId);

        return classesForStudent.stream()
                .map(cls -> {
                    Optional<Timetable> timetableOpt = timetableRepository.findByClassId(cls.getId());
                    Map<String, Object> result = Map.of(
                            "classInfo", cls,
                            "timetable", timetableOpt.orElse(null)
                    );
                    return result;
                })
                .collect(Collectors.toList());
    }

    public List<Timetable> getScheduleForTeacher(String teacherId) {
        List<Class> teacherClasses = classRepository.findByIdTeacher(teacherId);
        List<Timetable> teacherTimetables = new ArrayList<>();

        for (Class teacherClass : teacherClasses) {
            Optional<Timetable> timetableOptional = timetableRepository.findByClassId(teacherClass.getId());

            timetableOptional.ifPresent(teacherTimetables::add);
        }

        return teacherTimetables;
    }

}