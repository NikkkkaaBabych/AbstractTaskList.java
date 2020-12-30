package ua.edu.sumdu.j2se.studentName.tasks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.chrono.ChronoPeriod;
import java.time.temporal.ChronoField;
import java.util.Objects;

/**
 * class for make tasks
 * have title and time or times of doing
 * can be deactivated and activated
 */
public class Task implements Cloneable , Serializable {
    private String title;
    private LocalDateTime time;
    private LocalDateTime start;
    private LocalDateTime end;
    private int interval;
    private boolean active;

    /**
     * constructof for 1-time task
     *
     * @param title is name
     * @param time  1-time
     * @throws IllegalArgumentException if time<0
     */
    public Task(String title, LocalDateTime time) throws IllegalArgumentException {
        if( time == null || title == null )
            throw new IllegalArgumentException();
        this.title = title;
        this.time = time;
        this.start = time;
        this.end = time;
        active = false;
        interval = 0;
    }


    /**
     * constructor for task which is repeate
     */
    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) throws IllegalArgumentException {
        if (interval < 0) {
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

    public LocalDateTime getTime() {
        return (interval != 0 ? start : time);
    }

    public LocalDateTime getStartTime() {
        return (interval != 0 ? start : time);
    }

    public LocalDateTime getEndTime() {
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

    public void setTime(LocalDateTime time) throws IllegalArgumentException {
        interval = 0;
        this.time = time;

    }

    public void setTime(LocalDateTime start, LocalDateTime end, int interval) throws IllegalArgumentException {
        if (interval < 0) {
            throw new IllegalArgumentException();
        } else {
            this.start = start;
            this.end = end;
            this.interval = interval;
        }
    }

    /**
     * mothod geta time "current" and find next time for doing active task
     *
     * @param current time (type int) after which find next
     * @return
     * @throws IllegalArgumentException
     */
    public LocalDateTime nextTimeAfter(LocalDateTime current) throws IllegalArgumentException {

        if (interval == 0 & isActive()) {
            return time.isAfter(current) ? time : null;
        } else if (interval != 0 & isActive()) {
            if (current.isBefore(start)) {
                return start;
            }
            if (current.isAfter(end)) {
                return null;
            }
            Long repeatNumberAfter = (long) ((current.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC)) / ((double)interval) + 1);

            LocalDateTime timeAfter = start.plusSeconds( interval * repeatNumberAfter);
            if ( timeAfter.isBefore(end) || timeAfter.equals(end)) {
                return timeAfter;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    private long dateToSeconds(LocalDateTime time){
        return time.getLong(ChronoField.INSTANT_SECONDS);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", start=" + start +
                ", end=" + end +
                ", interval=" + interval +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        if (!isRepeated())
            return time.equals(task.time) &&
                    active == task.active &&
                    title.equals(task.title);
        else
            return start.equals(task.start) &&
                    end.equals(task.end) &&
                    interval == task.interval &&
                    active == task.active &&
                    title.equals(task.title);
    }

    @Override
    public int hashCode() {
        if (!isRepeated()) {
            return Objects.hash(title, time);
        } else {
            return Objects.hash(title, start, end, interval);
        }
    }

    @Override
    public Task clone() {
        try {
            Task newTask = (Task) super.clone();
            return newTask;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }


}
