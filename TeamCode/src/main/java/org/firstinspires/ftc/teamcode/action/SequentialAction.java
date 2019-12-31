package org.firstinspires.ftc.teamcode.action;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

import static java.lang.String.format;

public class SequentialAction implements RoboAction {

    private boolean isDone = false;
    private boolean isStarted = false;

    Queue<RoboAction> script;
    RoboAction currentAction;


    public SequentialAction() {
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
        if (!isStarted) return;

        if (null == currentAction || currentAction.isDone()) {
            if (currentAction != null) currentAction.stop();
            currentAction = script.poll();
            if (currentAction != null) currentAction.start();
        }
        if (currentAction != null) {
            currentAction.loop();
        } else {
            stop();
        }
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
