package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.todolist.classes.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FormActivity extends AppCompatActivity {

    private class SpinnerValues {
        private String name;
        private String status;

        SpinnerValues(String key, String value) {
            name = key;
            status = value;
        }

        public String toString () {
            return name;
        }

        public String getStatus () {
            return status;
        }
    }

    public void spinnerSetAdapter (Spinner taskStatusSpinner) {
        ArrayList<SpinnerValues> statusArray = new ArrayList<>();
        statusArray.add(new SpinnerValues("A Fazer", "0"));
        statusArray.add(new SpinnerValues("Fazendo", "1"));
        statusArray.add(new SpinnerValues("Feito", "2"));

        ArrayAdapter<SpinnerValues> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statusArray);
        taskStatusSpinner.setAdapter(spinnerAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_add);
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        FloatingActionButton fab = findViewById(R.id.fab);

        Spinner taskStatusSpinner = findViewById(R.id.SpinnerStatusTask);

        spinnerSetAdapter(taskStatusSpinner);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText taskTitle = findViewById(R.id.TitleTask);
                EditText taskDescription = findViewById(R.id.DescriptionTask);
                EditText taskDueDate = findViewById(R.id.DueDateTask); // commit these changes
                EditText taskDueTime = findViewById(R.id.DueTimeTask);
                String taskStatus = ((SpinnerValues) taskStatusSpinner.getSelectedItem()).getStatus();

                Task task = new Task();
                task.setTitle(taskTitle.toString());
                task.setDescription(taskDescription.toString());
                task.setDue_date(taskDueDate.toString() + " " + taskDueTime.toString());

                task.INSERT();
            }
        });
    }
}