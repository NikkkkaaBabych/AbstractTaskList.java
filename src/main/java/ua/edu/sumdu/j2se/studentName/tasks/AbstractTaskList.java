package ua.edu.sumdu.j2se.studentName.tasks;

import java.util.function.BiConsumer;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable{

    public AbstractTaskList(){}

    protected int size = 0;

    protected abstract AbstractTaskList getObj();

    public int getSize() {
        return size;
    }

    public abstract Task getTask(int index);

    public abstract void add (Task task) throws RuntimeException;

    public void addAll(AbstractTaskList tasks) throws RuntimeException{
        for(Object item: tasks){
            add((Task) item);
        }
    };

    public abstract boolean remove(Task wasteTask);

    public abstract Stream<Task> getStream();

    public final AbstractTaskList incoming(int from, int to) throws IllegalArgumentException{
        if (from < 0 || to < 0 || from > to){
            throw new IllegalArgumentException();
        }
        return this.getStream().filter(
                (Task task) -> task.getEndTime() > from &&
                task.nextTimeAfter(from) <= to &&
                task.nextTimeAfter(from) != -1 &&
                task.isActive()
        ).collect(
                () -> this.getObj(), (list, task)->list.add(task) , (list1, list2)-> list1.addAll(list2)
        );

        /*AbstractTaskList listForIncomingTasks = this.getObj();
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
        return listForIncomingTasks;*/
    }

    /*public AbstractTaskList incoming2  (int from, int to, AbstractTaskList taskList) throws IllegalArgumentException{
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
    }*/



}
