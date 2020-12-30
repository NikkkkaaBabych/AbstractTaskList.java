package ua.edu.sumdu.j2se.studentName.tasks;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TaskIO {

    public static void write(AbstractTaskList tasks, OutputStream out) throws IOException {
        DataOutputStream dout = new DataOutputStream(out);
        write(tasks,dout);
    }

    private static void write(AbstractTaskList tasks, DataOutputStream dout) throws IOException {
        dout.writeInt(tasks.getSize());
        for (Object taskO : tasks){
            Task task = (Task)taskO;
            dout.writeInt(task.getTitle().length());
            dout.write(task.getTitle().getBytes());
            dout.writeBoolean(task.isActive());
            dout.writeInt(task.getRepeatInterval());
            if (task.getRepeatInterval() == 0){
                dout.writeLong( task.getTime().toEpochSecond(ZoneOffset.UTC) );
                dout.writeInt(task.getTime().getNano());
                System.out.println();
            } else {
                dout.writeLong( task.getStartTime().toEpochSecond(ZoneOffset.UTC) );
                dout.writeInt(task.getStartTime().getNano());
                dout.writeLong( task.getEndTime().toEpochSecond(ZoneOffset.UTC) );
                dout.writeInt(task.getEndTime().getNano());
            }
        }
    }

    public static void read(AbstractTaskList tasks, InputStream in) throws IOException {
        DataInputStream din = new DataInputStream(in);
        read(tasks, din);
    }

    public static void writeBinary(AbstractTaskList tasks, File file) throws IOException {
        DataOutputStream dout = new DataOutputStream(new FileOutputStream(file));
        write(tasks,dout);
    }

    public static void readBinary(AbstractTaskList tasks, File file) throws IOException {
        DataInputStream din = new DataInputStream(new FileInputStream(file));
        read(tasks, din);
    }

    public static void read(AbstractTaskList tasks, DataInputStream din) throws IOException {
        int count = din.readInt();
        for (int i = 0; i < count; i++) {
            Task task;
            int titleLenght = din.readInt();
            byte[] titleSignes = new byte[titleLenght];
            din.read(titleSignes);

            boolean active = din.readBoolean();
            int interval = din.readInt();
            if (interval == 0){
                Long timeL = din.readLong();
                LocalDateTime time = LocalDateTime.ofEpochSecond(timeL, din.readInt() , ZoneOffset.UTC );
                task = new Task(new String(titleSignes, StandardCharsets.UTF_8),time);
            } else {
                Long startTimeL = din.readLong();
                LocalDateTime startTime = LocalDateTime.ofEpochSecond( startTimeL,  din.readInt() , ZoneOffset.UTC );
                Long endTimeL = din.readLong();
                LocalDateTime endTime = LocalDateTime.ofEpochSecond(endTimeL,  din.readInt() , ZoneOffset.UTC );
                task = new Task(new String(titleSignes, StandardCharsets.UTF_8),startTime,endTime,interval);
            }
            task.setActive(active);
            tasks.add(task);
        }
    }

    public static void write(AbstractTaskList tasks, Writer out) throws IOException {
        JsonObject result = new JsonObject();
        result.addProperty("size",tasks.getSize());
        JsonArray gtasks = new JsonArray();
        result.add("tasks",gtasks);
        for(Object taskO : tasks){
            Task task = (Task) taskO;
            JsonObject gtask = new JsonObject();
            gtask.addProperty("title", task.getTitle());
            gtask.addProperty("active", task.isActive());
            gtask.addProperty("interval", task.getRepeatInterval());
            if (task.getRepeatInterval() == 0){
                gtask.addProperty("time", task.getTime().toString());
            } else {
                gtask.addProperty("startTime", task.getStartTime().toString());
                gtask.addProperty("endTime", task.getEndTime().toString());
            }
            gtasks.add(gtask);
        }
        Gson jsonParser = new Gson();
        out.append(jsonParser.toJson(result) );
        out.close();
    }

    public static void read(AbstractTaskList tasks, Reader in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(in);
        JsonParser jsonParser = new JsonParser();
        JsonObject data = jsonParser.parse(bufferedReader.readLine()).getAsJsonObject();
        JsonArray jsonArray = data.getAsJsonArray("tasks");
        for(JsonElement taskE : jsonArray){
            Task taskO;
            JsonObject task = taskE.getAsJsonObject();
            if( task.get("interval").getAsInt() == 0 ) {
                taskO = new Task( task.get("title").getAsString() , LocalDateTime.parse( task.get("time").getAsString() ));
            } else {
                taskO = new Task( task.get("title").getAsString() , LocalDateTime.parse( task.get("startTime").getAsString() ), LocalDateTime.parse( task.get("endTime").getAsString() ) , task.get("interval").getAsInt());
            }
            taskO.setActive(task.get("active").getAsBoolean());
            tasks.add(taskO);
        }


    }

    public static void writeText(AbstractTaskList tasks, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        write(tasks,fileWriter);
    }

    public static void readText(AbstractTaskList tasks, File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        read(tasks,bufferedReader);
    }

}
