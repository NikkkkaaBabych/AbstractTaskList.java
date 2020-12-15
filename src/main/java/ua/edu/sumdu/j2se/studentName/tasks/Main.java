package ua.edu.sumdu.j2se.studentName.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) throws Exception {

		ArrayTaskList arrayTaskList = new ArrayTaskList();
		LinkedTaskList linkedTaskList = new LinkedTaskList();
		arrayTaskList.add(new Task("1", 1));
		arrayTaskList.add(new Task("2", 2));
		arrayTaskList.add(new Task("3", 3));
		arrayTaskList.add(new Task("4", 4));
		arrayTaskList.add(new Task("5", 5));
		linkedTaskList.add(new Task("1", 1));
		linkedTaskList.add(new Task("2", 2));
		linkedTaskList.add(new Task("3", 3));
		linkedTaskList.add(new Task("4", 4));
		linkedTaskList.add(new Task("5", 5));

		for ( Object a : arrayTaskList){
			System.out.println(a.hashCode());
		}

		System.out.println(IntStream.range(0, 0).toString());
		IntStream stream = IntStream.range(0,0);
		stream.forEach(System.out::println);

		ArrayTaskList arrayTaskList2 = new ArrayTaskList();

		List<Task> target = IntStream.range(0, 0).mapToObj(i -> arrayTaskList2.getTask(i)).collect(Collectors.toList());

		String s = target
				.stream()
				.map(t -> "Task{title:'" + t.getTitle()
						+ "',time:" + t.getTime()
						+ ",startTime:" + t.getStartTime()
						+ ",endTime:" + t.getEndTime()
						+ ",repeatInterval:" + t.getRepeatInterval()
						+ ",active:" + t.isActive()
						+ "}")
				.collect(Collectors.joining(","));

		System.out.println("s" + s + "s");

	}

}
