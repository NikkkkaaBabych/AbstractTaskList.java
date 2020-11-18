package ua.edu.sumdu.j2se.studentName.tasks;

import java.util.AbstractList;
import java.util.Iterator;

public abstract class AbstractTaskList {

    protected int size = 0;

    protected abstract AbstractTaskList getObj();

    public int getSize() {
        return size;
    }

    public abstract Task getTask(int index) throws IndexOutOfBoundsException;

    public abstract void add (Task task) throws Exception;

    public abstract boolean remove(Task wasteTask);

    public AbstractTaskList incoming(int from, int to) throws IllegalArgumentException{
        if (from < 0 || to < 0 || from > to){
            throw new IllegalArgumentException();
        } else {
            AbstractTaskList listForIncomingTasks = this.getObj();
            for (int i = 0; i < this.getSize(); i++) {
                Task task = this.getTask(i);
                if (task.getEndTime() > from &
                        task.nextTimeAfter(from) <= to &
                        task.nextTimeAfter(from) != -1) {
                    try {
                        listForIncomingTasks.add(task);
                    } catch (Exception ex){
                        continue;
                    }
                } else {
                    continue;
                }
            }
            return listForIncomingTasks;
        }
    }

    public AbstractTaskList incoming2  (int from, int to, AbstractTaskList taskList) throws IllegalArgumentException{
        if (from < 0 || to < 0 || from > to){
            throw new IllegalArgumentException();
        } else {
            for (int i = 0; i < taskList.getSize(); i++) {
                Task task = taskList.getTask(i);
                if (task.getEndTime() > from &
                        task.nextTimeAfter(from) <= to &
                        task.nextTimeAfter(from) != -1) {
                    try {
                        this.add(task);
                    } catch (Exception ex){
                        continue;
                    }
                } else {
                    continue;
                }
            }
            return this;
        }
    }
}
