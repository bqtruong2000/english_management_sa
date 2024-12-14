package org.example.englishmanagement.util.TimetableCommand;

import org.example.englishmanagement.model.Timetable;
import org.example.englishmanagement.service.TimetableService;
import org.example.englishmanagement.util.Command;

import java.util.List;

// CreateTimetableCommand.java
public class CreateTimetableCommand implements Command {

    private TimetableService timetableService;
    private String classId;
    private List<Timetable.ScheduleItem> schedule;

    public CreateTimetableCommand(TimetableService timetableService, String classId, List<Timetable.ScheduleItem> schedule) {
        this.timetableService = timetableService;
        this.classId = classId;
        this.schedule = schedule;
    }

    @Override
    public void execute() {
        timetableService.createTimeTable(classId, schedule);
    }
}

