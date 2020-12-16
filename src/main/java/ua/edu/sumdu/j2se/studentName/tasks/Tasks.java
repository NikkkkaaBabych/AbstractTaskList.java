package ua.edu.sumdu.j2se.studentName.tasks;

        import java.time.LocalDateTime;
        import java.time.ZoneId;
        import java.time.ZoneOffset;
        import java.util.*;

public class Tasks {

    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime from, LocalDateTime to) {

        if (from.isAfter(to) || tasks == null || from == null || to == null) {
            throw new IllegalArgumentException();
        }

        AbstractTaskList result = new LinkedTaskList();

        for (Task task : tasks) {
            try {
                LocalDateTime event = task.nextTimeAfter(from);
                if (    task.isActive() && ( event.isEqual(from) || event.isEqual(to) || (event.isBefore(to) && event.isAfter(from)))) {
                    result.add(task);
                }
            } catch (NullPointerException e) {}
        }

        return result;
    }

    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime from, LocalDateTime to) {
        if (tasks == null || from == null || to == null)
            throw new NullPointerException();
        if (from.isAfter(to))
            return null;

        SortedMap<LocalDateTime, Set<Task>> result = new TreeMap<LocalDateTime, Set<Task>>();

        for (Task task : tasks) {
            for (LocalDateTime key = task.nextTimeAfter(from); key.isBefore(to) || key.isEqual(to) ; key = task.nextTimeAfter(key)) {
                if ( key == null )
                    break;

                if (result.containsKey(key)) {
                    Set<Task> set = result.get(key);
                    set.add(task);
                } else {
                    Set<Task> newSet = new HashSet<>();
                    newSet.add(task);
                    result.put(key, newSet);
                }
            }



        }
        return result;
    }

}
