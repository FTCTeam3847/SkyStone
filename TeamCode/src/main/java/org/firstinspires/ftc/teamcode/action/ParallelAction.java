package org.firstinspires.ftc.teamcode.action;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

import static java.lang.String.format;

public class ParallelAction implements RoboAction {

    private boolean isDone = false;
    private boolean isStarted = false;

    Queue<RoboAction> script;
    RoboAction currentAction;


    public ParallelAction() {
        script = new LinkedList<>();
    }

    public void addAction(RoboAction action) {
        script.add(action);
    }

    @Override
    public void start() {
        isStarted = true;
    }

    @Override
    public void loop() {

    }

    @Override
    public void stop() {
        isDone = true;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public boolean isStarted() {
        return isStarted;
    }

    @Override
    public String toString() {
        return format(Locale.US,
                "%s",
                currentAction
        );
    }
}
