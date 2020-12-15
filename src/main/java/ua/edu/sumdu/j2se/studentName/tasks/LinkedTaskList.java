package ua.edu.sumdu.j2se.studentName.tasks;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LinkedTaskList extends AbstractTaskList implements Cloneable{

    private Node firstNode = null;

    public LinkedTaskList() {
        size = 0;
    }

    private class Node {
        private Task task = null;
        private Node nextNode = null;

        public Node(){
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(task, node.task);
        }

        @Override
        public int hashCode() {
            return task.hashCode();
        }
    }

    public int size(){
        return size;
    }


    @Override
    public String toString() {
        String result = "LinkedTaskList{ ";
        for( Object task : LinkedTaskList.this){
            result+= task + " ";
        }
        return result + '}';
    }

    @Override
    public AbstractTaskList getObj() {
        AbstractTaskList newList = new LinkedTaskList();
        return newList;
    }

    @Override
    public Task getTask(int index) throws IndexOutOfBoundsException{
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        } else {
            Node iterator = firstNode;
            for (int i = 0; i < index; i++) {
                if (iterator.nextNode != null) {
                    iterator = iterator.nextNode;
                } else {
                    break;
                }
            }
            return iterator.task;
        }
    }

    @Override
    public void add (Task task) throws RuntimeException{
        if (task == null) {
            throw new NullPointerException("can't add 'null'");
        } else {
            Node newNode = new Node();
            newNode.task = task;
            if( firstNode == null ) {
                firstNode = newNode;
                size++;
                return;
            }
            Node iterator = firstNode;

            while(iterator.nextNode != null) {
                iterator = iterator.nextNode;
            }
            iterator.nextNode = newNode;
            size++;
        }
    }

    @Override
    public boolean remove(Task wasteTask) {
        if ( wasteTask == null )
            throw new IllegalStateException();
        Node iterator = firstNode;
        Node previousIterator = firstNode;
        if ( firstNode.task.equals(wasteTask)){
            firstNode = firstNode.nextNode;
            size--;
            return true;
        }
        while(true) {
            if(iterator.task.equals(wasteTask)){
                previousIterator.nextNode = iterator.nextNode;
                size--;
                return true;
            }
            if (iterator.nextNode == null) {
                break;
            } else {
                previousIterator = iterator;
                iterator = iterator.nextNode;
            }
        }
        return false;
    }

    @NotNull
    @Override
    public Iterator iterator() {
        return new LinkedTaskList.LinkedTaskListIterator();
    }

    private class LinkedTaskListIterator implements Iterator<Task>{
        Node node;
        {
            node = new Node();
            node.nextNode = firstNode;
        }

        @Override
        public boolean hasNext() {
            return node.nextNode == null ? false : true;
        }

        @Override
        public Task next() {
            if ( node.nextNode == null )
                throw new NoSuchElementException();
            node = node.nextNode;
            return node.task;
        }

        @Override
        public void remove() {
            LinkedTaskList.this.remove(node.task);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedTaskList that = (LinkedTaskList) o;
        Iterator<Task> thatIterator = that.iterator();
        Iterator<Task> thisIterator = this.iterator();
        try {
            for( ; thatIterator.hasNext() || thisIterator.hasNext(); ){
                if ( !thisIterator.next().equals(thatIterator.next()) ) return false;
            }
        } catch ( NoSuchElementException e) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;

        for (Object element : this)
            result = 31 * result + (element == null ? 0 : element.hashCode());

        return result;
    }

    @Override
    public Object clone() {
        try{
            LinkedTaskList copy = (LinkedTaskList)super.clone();
            copy.size = this.size;
            copy.firstNode = this.firstNode;
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
