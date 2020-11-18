package ua.edu.sumdu.j2se.studentName.tasks;

/**
 * class for make tasks
 * have title and time or times of doing
 * can be deactivated and activated
 */
public class Task {
    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean active;

    /**
     * constructof for 1-time task
     * @param title is name
     * @param time 1-time
     * @throws IllegalArgumentException if time<0
     */
    public Task(String title, int time) throws IllegalArgumentException{
        if (time < 0){
            throw new IllegalArgumentException();
        } else {
            this.title = title;
            this.time = time;
            this.start = time;
            this.end = time;
            active = false;
            interval = 0;
        }
    }

    public boolean equals(@org.jetbrains.annotations.NotNull Task task) {
        if(getTitle() == task.title &
                getTime() == task.time &
                getStartTime() == task.start &
                getEndTime() == task.end &
                getRepeatInterval() == task.interval) {
            return true;
        }else{
            return false;
        }
    }

    /**
 * constructor for task which is repeate
 */
    public Task(String title, int start, int end, int interval) throws IllegalArgumentException{
        if (start < 0 || end < 0 || interval < 0){
            throw new IllegalArgumentException();
        } else {
            this.title = title;
            this.start = start;
            this.end = end;
            this.time = start;
            this.interval = interval;
            active = false;
        }
    }

    public boolean isActive() {
        return active;
    }

    public boolean isRepeated() {
        return (interval != 0 ? true : false);
    }

    public String getTitle() {
        return title;
    }

    public int getTime() {
        return (interval != 0 ? start : time);
    }

    public int getStartTime() {
        return (interval != 0 ? start : time);
    }

    public int getEndTime() {
        return (interval != 0 ? end : time);
    }

    public int getRepeatInterval() {
        return (interval != 0 ? interval : 0);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setTime(int time) throws IllegalArgumentException{
        if (time < 0){
            throw new IllegalArgumentException();
        } else {
            interval = 0;
            this.time = time;
        }
    }

    public void setTime(int start, int end, int interval) throws IllegalArgumentException{
        if (start < 0 || end < 0 || interval < 0){
            throw new IllegalArgumentException();
        } else {
            this.start = start;
            this.end = end;
            this.interval = interval;
        }
    }

    /**
     * mothod geta time "current" and find next time for doing active task
     * @param current time (type int) after which find next
     * @return
     * @throws IllegalArgumentException
     */
    public int nextTimeAfter(int current) throws IllegalArgumentException{
        if (current < 0){
            throw new IllegalArgumentException();
        } else {
            if (interval == 0 & isActive()) {
                return ((time > current) ? time : -1);
            } else if (interval != 0 & isActive()) {
                if (current < start) {
                    return start;
                }
                if (current > end) {
                    return -1;
                }
                int repeatNumberAfter = (current - start) / interval + 1;
                int timeAfter = start + interval * repeatNumberAfter;
                if (timeAfter < end) {
                    return timeAfter;
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        }
    }
}
