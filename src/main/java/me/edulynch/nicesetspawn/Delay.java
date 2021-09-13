package me.edulynch.nicesetspawn;

import org.bukkit.scheduler.BukkitTask;

public class Delay {

    private BukkitTask task;
    private int time;
    private int startX;
    private int startY;
    private int startZ;

    public Delay(BukkitTask task, int startX, int startY, int startZ) {
        this.task = task;
        this.time = 1;
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
    }

    public BukkitTask getTask() {
        return task;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getStartZ() {
        return startZ;
    }

}
