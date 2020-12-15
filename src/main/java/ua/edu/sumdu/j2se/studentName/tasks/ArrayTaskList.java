package ua.edu.sumdu.j2se.studentName.tasks;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ArrayTaskList extends AbstractTaskList implements Cloneable{

    private Task[] array;
    private static final int stepForNewArray = 10;
    private int currentEdge;

    public ArrayTaskList() {
        size = 0;
        array = new Task[stepForNewArray];
        currentEdge = stepForNewArray;
    }

    @Override
    public AbstractTaskList getObj() {
        AbstractTaskList newList = new ArrayTaskList();
        return newList;
    }

    public int size(){
        return size;
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
    public void add(Task task) throws RuntimeException {//– метод, що додає до списку вказану задачу.
        if (task == null) {
            throw new NullPointerException("can't add 'null'");
        } else {
            int newSize = size + 1;
            if (newSize > currentEdge) {
                Task[] newArray = new Task[size + stepForNewArray];
                currentEdge = size + stepForNewArray;
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
                currentEdge = size - 1;
                System.arraycopy(array, 0, newarray, 0, i);
                System.arraycopy(array, i + 1, newarray, i, size - i - 1);
                size = newsize;
                array = newarray;
                return true;
            }
        }
        return false;
    }

    @NotNull
    @Override
    public Iterator<Task> iterator() {
        return new ArrayTaskList.ArrayTaskListIterator();
    }


    private class ArrayTaskListIterator implements Iterator<Task> {

        int cursor = 0;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such

        ArrayTaskListIterator() {}

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public Task next() {
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            cursor = i + 1;
            return array[lastRet = i];
        }

        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            ArrayTaskList.this.remove(array[lastRet]);
            cursor = lastRet;
            lastRet = -1;
        }
    }

    @Override
    public String toString() {
        return "ArrayTaskList{" +
                "array=" + Arrays.toString(array) +
                ", size=" + size +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayTaskList that = (ArrayTaskList) o;
        return Arrays.equals(array, that.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    @Override
    public Object clone() {
        try{
            ArrayTaskList copy = (ArrayTaskList)super.clone();
            copy.size = this.size;
            copy.array = this.array.clone();
            return copy;
        }
        catch(CloneNotSupportedException e){
            throw new AssertionError("Impossible");
        }
    }

    @Override
    public Stream<Task> getStream() {
        return IntStream.range(0,size).mapToObj(this::getTask);
    }

}