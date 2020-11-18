package ua.edu.sumdu.j2se.studentName.tasks;
import java.util.Iterator;
import java.util.LinkedList;

class LinkedTaskList {
    private int size = 0;
    private Node firstNode = null;

    private class Node {
        private Task task = null;
        private int index = 0;
        private LinkedTaskList.Node nextNode = null;

        public Node(){
        }
    }

    public LinkedTaskList(){
    }

    public int getSize() {
        return size;
    }

    public Task getTask(int index) throws IndexOutOfBoundsException{
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        } else {
            Node iterator = firstNode;
            while (true) {
                if (iterator.index == index) {
                    return iterator.task;
                }
                if (iterator.nextNode == null) {
                    break;
                } else {
                    iterator = iterator.nextNode;
                }
            }
            return null;
        }
    }

    public void add (Task task) throws Exception{//– метод, що додає до списку вказану задачу.
        if (task == null) {
            throw new Exception("can't add 'null'");
        } else {
            Node newNode = new Node();
            newNode.index = size;
            newNode.task = task;
            Node iterator = firstNode;

            while(iterator.nextNode != null) {
                iterator = iterator.nextNode;
            }
            iterator.nextNode = newNode;
            size++;
        }
    }

    public boolean remove(Task task) {
        Node iterator = firstNode;
        Node previousIterator = firstNode;
        while(true) {
            if(iterator.task == task){

                previousIterator.nextNode = iterator.nextNode;
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

    public boolean remove(String title, int time, int start, int end, int interval) {//– метод, що видаляє задачу зі списку і повертає істину, якщо

        Node iterator = firstNode;
        Node previousIterator = firstNode;
        while(true) {
            if(iterator.task.getTitle() == title &
                    iterator.task.getTime() == time &
                    iterator.task.getStartTime() == start &
                    iterator.task.getEndTime() == end &
                    iterator.task.getRepeatInterval() == interval){

                previousIterator.nextNode = iterator.nextNode;
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


    public LinkedTaskList incoming(int from, int to) throws IllegalArgumentException{
        if (from < 0 || to < 0 || from > to){
            throw new IllegalArgumentException();
        } else {
            LinkedTaskList incoming = new LinkedTaskList();
            Node iterator = firstNode;
            while(true) {
                if (iterator.task.getEndTime() > from &
                        iterator.task.nextTimeAfter(from) <= to &
                        iterator.task.nextTimeAfter(from) != -1) {
                    try {
                        incoming.add(iterator.task);
                    } catch (Exception ex){
                        continue;
                    }
                }
                if (iterator.nextNode == null) {
                    break;
                } else {
                    iterator = iterator.nextNode;
                }
            }
            return incoming;
        }
    }
}
