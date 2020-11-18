package ua.edu.sumdu.j2se.studentName.tasks;

public class ArrayTaskList {

    private int size;
    private Task [] array;
    private static final int stepForNewArray = 10;

    public ArrayTaskList(){
        size = 0;
        array = new Task [stepForNewArray];
    }

    public void add(Task task) throws Exception{//– метод, що додає до списку вказану задачу.
        if (task == null) {
            throw new Exception("can't add 'null'");
        } else {
            int newSize = size + 1;
            if (newSize % stepForNewArray == 1){
                Task[] newArray = new Task[size + stepForNewArray];
                System.arraycopy(array, 0, newArray, 0, size);
                array = newArray;
            }
            array[newSize-1] = task;
            size = newSize;
        }
    }

    public boolean remove(String title, int time, int start, int end, int interval) {//– метод, що видаляє задачу зі списку і повертає істину, якщо
        //така задача була у списку. Якщо у списку було декілька таких задач, необхідно видалити
        //одну будь-яку.
        for(int i = 0; i < size; i ++){
            if (array[i].getTitle() == title &
                    array[i].getTime() == time &
                    array[i].getStartTime() == start &
                    array[i].getEndTime() == end &
                    array[i].getRepeatInterval() == interval
            ){
                int newsize = size - 1;
                Task [] newarray = new Task [size - 1];
                System.arraycopy(array, 0, newarray, 0, i);
                System.arraycopy(array, i + 1, newarray, i, size - i - 1);
                size = newsize;
                array = newarray;
                return true;
            }
        }
        return false;
    }

    public int getSize() {//– метод, що повертає кількість задач у списку.
        return size;
    }

    public Task getTask(int index) throws IndexOutOfBoundsException{//– метод, що повертає задачу, яка знаходиться на вказаному
        // місці у списку, перша задача має індекс 0.
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        } else {
            return array[index];
        }
    }

    public ArrayTaskList incoming(int from, int to) throws IllegalArgumentException{
        if (from < 0 || to < 0 || from > to){
            throw new IllegalArgumentException();
        } else {
            ArrayTaskList incoming = new ArrayTaskList();
            for (int i = 0; i < size; i++) {
                if (array[i].getEndTime() > from &
                        array[i].nextTimeAfter(from) <= to &
                        array[i].nextTimeAfter(from) != -1) {
                    try {
                        incoming.add(array[i]);
                    } catch (Exception ex){
                        continue;
                    }
                } else {
                    continue;
                }
            }
            return incoming;
        }
    }
}
