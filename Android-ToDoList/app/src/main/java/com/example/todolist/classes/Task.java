package com.example.todolist.classes;

import android.util.Log;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Task {
    private int ID;
    private String title;
    private String description;
    private Date due_date;
    private int status;

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDue_date(String due_date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            this.due_date = dateFormat.parse(due_date);
        } catch(Exception e) {
            System.out.println("Erro: " + e.getClass());
            e.printStackTrace();
        }
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDue_dateString () {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        return dateFormat.format(this.getDue_date());
    }

    public Date getDue_date() {
        return due_date;
    }

    public int getStatus() {
        return status;
    }

    public Task () { }

    public Task (String title, String description, String due_date, int status) {
        this.setTitle(title);
        this.setDescription(description);
        this.setDue_date(due_date);
        this.setStatus(status);
    }

    public static ArrayList<Task> getTasksByString (String rows) {
        ArrayList<Task> tasks = new ArrayList<>();
        String[] lines = rows.split("\\<br>");

        for(String row : lines) {
            String[] columns = row.split("\\,");

            Task task = new Task();
            task.setID(Integer.parseInt(columns[0]));
            task.setTitle(columns[1]);
            task.setDescription(columns[2]);
            task.setDue_date(columns[3]);
            task.setStatus(Integer.parseInt(columns[4]));

            tasks.add(task);
        }

        return tasks;
    }

    public static ArrayList<Task> SELECT (String search) {
        String link = "http://192.168.13.231/AndroidPages/index.php";
        String[][] getArray = {{"search", search}};

        return getTasksByString(Server.Execute(link, getArray, new String[1][2]));
    }

    public String INSERT () {
        String link = "http://192.168.13.231/cadastrar.php";
        String[][] postArray = {
                {"title", getTitle()},
                {"description", getDescription()},
                {"date", getDue_dateString()},
                {"status", String.valueOf(getStatus())}
        };

        return Server.Execute(link, null, postArray);
    }
}
