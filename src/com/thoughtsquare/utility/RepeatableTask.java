package com.thoughtsquare.utility;

import android.os.Handler;

public class RepeatableTask implements Runnable{
    private Handler handler;
    private Runnable task;
    private long delay;

    public RepeatableTask(Handler handler, Runnable task, long delay){
        this.handler = handler;
        this.task = task;
        this.delay = delay;
    }

    public void run() {
        task.run();
        handler.postDelayed(task, delay);
    }

    public Runnable getTask() {
        return task;
    }
}
