package ua.edu.sumdu.j2se.studentName.tasks;
import java.util.Iterator;

class LinkedTaskList extends AbstractTaskList{

    private Node firstNode = null;

    private class Node {
        private Task task = null;
        private Node nextNode = null;

        public Node(){
        }
    }

    public LinkedTaskList(){
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
    public void add (Task task) throws Exception{
        if (task == null) {
            throw new Exception("can't add 'null'");
        } else {
            Node newNode = new Node();
            newNode.task = task;
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
        Node iterator = firstNode;
        Node previousIterator = firstNode;
        while(true) {
            if(iterator.task.equals(wasteTask)){
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
}
