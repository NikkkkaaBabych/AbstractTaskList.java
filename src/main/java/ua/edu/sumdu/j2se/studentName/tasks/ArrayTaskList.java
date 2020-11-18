package ua.edu.sumdu.j2se.studentName.tasks;

public class ArrayTaskList extends AbstractTaskList {

    private Task[] array;
    private static final int stepForNewArray = 10;

    public ArrayTaskList() {
        size = 0;
        array = new Task[stepForNewArray];
    }

    @Override
    public AbstractTaskList getObj() {
        AbstractTaskList newList = new ArrayTaskList();
        return newList;
    }

    @Override
    public Task getTask(int index) throws IndexOutOfBoundsException {//– метод, що повертає задачу, яка знаходиться на вказаному
        // місці у списку, перша задача має індекс 0.
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        } else {
            return array[index];
        }
    }

    @Override
    public void add(Task task) throws Exception {//– метод, що додає до списку вказану задачу.
        if (task == null) {
            throw new Exception("can't add 'null'");
        } else {
            int newSize = size + 1;
            if (newSize % stepForNewArray == 1) {
                Task[] newArray = new Task[size + stepForNewArray];
                System.arraycopy(array, 0, newArray, 0, size);
                array = newArray;
            }
            array[newSize - 1] = task;
            size = newSize;
        }
    }

    @Override
    public boolean remove(Task wasteTask) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(wasteTask)) {
                int newsize = size - 1;
                Task[] newarray = new Task[size - 1];
                System.arraycopy(array, 0, newarray, 0, i);
                System.arraycopy(array, i + 1, newarray, i, size - i - 1);
                size = newsize;
                array = newarray;
                return true;
            }
        }
        return false;
    }
}