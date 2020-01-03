package org.firstinspires.ftc.teamcode.action;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

import static java.lang.String.format;

public class ParallelAction extends SequentialAction{

    private boolean isDone = false;
    private boolean isStarted = false;

    boolean actionDone = false;
    int numTerms = 0;

    Queue<RoboAction> script;

    public ParallelAction() {
        script = new LinkedList<>();
    }

    public ParallelAction(Queue<RoboAction> script) {
        this.script = script;
    }

    public void addAction(RoboAction... actions) {
        for(RoboAction action: actions)
        {
            script.add(action);
            numTerms++;
        }
    }

    @Override
    public void start() {
        isStarted = true;

        for(int i = 0; i < numTerms; i++) {
            RoboAction action = script.poll();
            script.add(action);
            action.start();
        }
    }

    @Override
    public void loop() {
        if (!isStarted || isDone) return;

        actionDone = false;

        for(int i = 0; i < numTerms; i++) {
            RoboAction action = script.poll();
            if(action != null && !action.isDone())
            {
                action.loop();
                actionDone = true;
            }
        }

        if(!actionDone)
        {
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
                numTerms
        );
    }
}
