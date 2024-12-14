package org.example.englishmanagement.controller;

import org.example.englishmanagement.model.Timetable;
import org.example.englishmanagement.service.TimetableService;
import org.example.englishmanagement.util.Command;
import org.example.englishmanagement.util.JwtUtil;
import org.example.englishmanagement.util.TimetableCommand.CreateTimetableCommand;
import org.example.englishmanagement.util.TimetableCommand.DeleteTimetableCommand;
import org.example.englishmanagement.util.TimetableCommand.TimetableInvoker;
import org.example.englishmanagement.util.TimetableCommand.UpdateTimetableCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/timetable")
public class TimetableController {

    @Autowired
    private TimetableService timetableService;

    @GetMapping("/classes-by-date")
    public List<Map<String, String>> getClassesByDate(
            @RequestParam String date,
            @RequestHeader("Authorization") String token) {
        JwtUtil.validateAdminAccess(token);
        return timetableService.getClassesByDate(date);
    }


    @PostMapping("/create/{classId}")
    public ResponseEntity<?> createTimeTable(
            @PathVariable String classId,
            @RequestBody List<Timetable.ScheduleItem> schedule,
            @RequestHeader("Authorization") String token
    ) {
        JwtUtil.validateAdminAccess(token);

        Command createCommand = new CreateTimetableCommand(timetableService, classId, schedule);

        TimetableInvoker invoker = new TimetableInvoker();
        invoker.setCommand(createCommand);
        invoker.invoke();

        return ResponseEntity.ok("Timetable created successfully.");
    }

    @PutMapping("/update/{classId}")
    public ResponseEntity<?> updateTimeTable(
            @PathVariable String classId,
            @RequestBody List<Timetable.ScheduleItem> newSchedule,
            @RequestHeader("Authorization") String token
    ) {
        JwtUtil.validateAdminAccess(token);

        Command updateCommand = new UpdateTimetableCommand(timetableService, classId, newSchedule);

        TimetableInvoker invoker = new TimetableInvoker();
        invoker.setCommand(updateCommand);
        invoker.invoke();

        return ResponseEntity.ok("Timetable updated successfully.");
    }

    @GetMapping("/get/{classId}")
    public ResponseEntity<?> getTimeTable(
            @PathVariable String classId,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(timetableService.getTimeTable(classId));
    }

    @DeleteMapping("/delete/{classId}")
    public ResponseEntity<?> deleteTimeTable(
            @PathVariable String classId,
            @RequestHeader("Authorization") String token
    ) {
        JwtUtil.validateAdminAccess(token);

        Command deleteCommand = new DeleteTimetableCommand(timetableService, classId);

        TimetableInvoker invoker = new TimetableInvoker();
        invoker.setCommand(deleteCommand);
        invoker.invoke();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentClassesAndTimetable(
            @PathVariable String studentId,
            @RequestHeader("Authorization") String token) {
        JwtUtil.validateStudentAccess(token);
        return ResponseEntity.ok(timetableService.getStudentClassesAndTimetables(studentId));
    }

    @GetMapping("/teacher/{teacherId}")
    public List<Timetable> getScheduleForTeacher(@PathVariable String teacherId) {
        return timetableService.getScheduleForTeacher(teacherId);
    }

}