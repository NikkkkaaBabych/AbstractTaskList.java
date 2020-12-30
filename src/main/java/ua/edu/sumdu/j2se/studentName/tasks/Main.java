package ua.edu.sumdu.j2se.studentName.tasks;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) throws Exception {

		ArrayTaskList arrayTaskList = new ArrayTaskList();
		arrayTaskList.add(new Task("asdfdf", LocalDateTime.now()));
		arrayTaskList.add(new Task("adssdfdf", LocalDateTime.now()));
		arrayTaskList.add(new Task("asdwqfdf", LocalDateTime.now()));
		arrayTaskList.add(new Task("asdfwdf", LocalDateTime.now()));
		File file = new File("asdf.json");
		TaskIO.write(arrayTaskList, new FileWriter(file));
	}

	public static int[] sortArray(int[] array) {
		//List<Integer> keys = new ArrayList<>();
		//for( int i = 0 ; i < array.length; i++ )
		//	if( array[i] % 2 == 0 ){}


		return array;
	}


}
