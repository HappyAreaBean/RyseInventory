package io.github.rysefoxx.inventory.plugin.util;

import org.jetbrains.annotations.NotNull;
import space.arim.morepaperlib.scheduling.RegionalScheduler;
import space.arim.morepaperlib.scheduling.ScheduledTask;

public abstract class GlobalRunnable implements Runnable {
    private final RegionalScheduler scheduler;

    private ScheduledTask scheduledTask;

    public GlobalRunnable(RegionalScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public synchronized boolean isCancelled() {
        checkScheduled();
        return scheduledTask.getExecutionState() == ScheduledTask.ExecutionState.CANCELLED;
    }

    public synchronized void cancel() {
        checkScheduled();
        scheduledTask.cancel();
    }

    public synchronized ScheduledTask runDelayed(long delay) {
        checkNotYetScheduled();
        return setupTask(scheduler.runDelayed(this, delay));
    }

    public synchronized ScheduledTask runAtFixedRate(long initialDelay, long period) {
        checkNotYetScheduled();
        return setupTask(scheduler.runAtFixedRate(this, initialDelay, period));
    }

    private void checkScheduled() {
        if (scheduledTask == null) {
            throw new IllegalStateException("Not scheduled yet");
        }
    }

    private void checkNotYetScheduled() {
        if (this.scheduledTask != null) {
            throw new IllegalStateException("Already scheduled as " + this.scheduledTask);
        }
    }

    private ScheduledTask setupTask(@NotNull ScheduledTask scheduledTask) {
        this.scheduledTask = scheduledTask;
        return scheduledTask;
    }
}
