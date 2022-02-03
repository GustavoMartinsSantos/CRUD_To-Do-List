package com.example.todolist.classes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        this.description = description.replaceAll("<br />", "\n");
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

    public void setBRDue_date(String due_date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {
            this.due_date = dateFormat.parse(due_date);
        } catch(Exception e) {
            System.out.println("Erro: " + e.getClass());
            e.printStackTrace();
        }
    }

    public void setStatus(int status) {
        if(status == 3)
            status = 0;

        if(getDue_date().before(new Date()) && status != 2)
            status = 3;

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

    public String getBRDue_dateTime () {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        return dateFormat.format(this.getDue_date());
    }

    public String getDue_dateString () {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return dateFormat.format(this.getDue_date());
    }

    public String getDue_timeString () {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

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
        String[] lines = rows.split("\\<tr><td>");

        for(String row : lines) {
            String[] columns = row.split("\\<td>");

            if(columns.length == 5) {
                Task task = new Task();
                int status = Integer.parseInt(columns[4]);

                task.setID(Integer.parseInt(columns[0]));
                task.setTitle(columns[1]);
                task.setDescription(columns[2]);
                task.setDue_date(columns[3]);
                task.setStatus(status);

                if(task.getStatus() != status)
                    task.UPDATE();

                tasks.add(task);
            }
        }

        return tasks;
    }

    public String INSERT () {
        String link = "http://192.168.13.232/cadastrar.php";
        String[][] postArray = {
                {"title", getTitle()},
                {"description", getDescription()},
                {"date", getDue_dateString()},
                {"time", getDue_timeString()},
                {"status", String.valueOf(getStatus())}
        };

        return Server.Execute(link, null, postArray);
    }

    public static Task SELECT (int id) {
        String link = "http://192.168.13.232/AndroidPages/index.php";
        String[][] getArray = {{"id", String.valueOf(id)}};

        return getTasksByString(Server.Execute(link, getArray, null)).get(0);
    }

    public static ArrayList<Task> SELECT (String search, String status) {
        String link = "http://192.168.13.232/AndroidPages/index.php";
        String[][] getArray = {{"search", search},
                               {"status", status}};

        return getTasksByString(Server.Execute(link, getArray, null));
    }

    public String UPDATE () {
        String link = "http://192.168.13.232/editar.php";

        String[][] getArray = {
                {"id", String.valueOf(getID())}
        };

        String[][] postArray = {
                {"title", getTitle()},
                {"description", getDescription()},
                {"date", getDue_dateString()},
                {"time", getDue_timeString()},
                {"status", String.valueOf(getStatus())}
        };

        return Server.Execute(link, getArray, postArray);
    }

    public String DELETE () {
        String link = "http://192.168.13.232/excluir.php";
        String[][] getArray = {{"id", String.valueOf(getID())}};

        return Server.Execute(link, getArray, null);
    }
}
