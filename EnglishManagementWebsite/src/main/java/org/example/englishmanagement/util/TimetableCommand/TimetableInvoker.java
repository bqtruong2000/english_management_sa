package org.example.englishmanagement.util.TimetableCommand;

import org.example.englishmanagement.util.Command;

public class TimetableInvoker {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void invoke() {
        command.execute();
    }
}

