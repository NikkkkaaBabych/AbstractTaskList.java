package ua.edu.sumdu.j2se.studentName.tasks;

import java.time.LocalDateTime;
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

    public final AbstractTaskList incoming(LocalDateTime from, LocalDateTime to) throws IllegalArgumentException{
        if (from.isAfter(to)){
            throw new IllegalArgumentException();
        }

/*            AbstractTaskList listForIncomingTasks = this.getObj();
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
        return this.getStream().filter((Task task) -> task.getEndTime().isAfter(from) &&
                task.nextTimeAfter(from).isBefore(to) &&
                task.nextTimeAfter(from) != null && task.isActive()).collect( () -> this.getObj(), (list, item)->list.add(item) , (list1, list2)-> list1.addAll(list2));
    }

    public abstract Stream<Task> getStream();

}
