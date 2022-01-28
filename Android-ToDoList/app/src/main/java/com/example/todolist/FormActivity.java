package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todolist.classes.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Bundle lastActivityBundle;
    private Task task = new Task();
    private EditText taskTitle;
    private EditText taskDesc;
    private Spinner taskStatusSpinner;
    private TextView taskDueDate;
    private TextView taskDueTime;

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
        statusArray.add(new SpinnerValues("Atrasado", "3"));

        ArrayAdapter<SpinnerValues> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statusArray);
        taskStatusSpinner.setAdapter(spinnerAdapter);
    }

    private void showDateDialog () {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (DatePickerDialog.OnDateSetListener) this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.show();
    }

    private void showTimeDialog () {
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hour = String.valueOf(hourOfDay);
                String minuteOfHour = String.valueOf(minute);

                if(hourOfDay < 10)
                    hour = "0" + hour;
                if(minute < 10)
                    minuteOfHour = "0" + minuteOfHour;

                taskDueTime.setText(hour + ":" + minuteOfHour);
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                listener,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
        );

        timePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month += 1;
        String day = String.valueOf(dayOfMonth);
        String monthOfYear = String.valueOf(month);

        if(dayOfMonth < 10)
            day = "0" + day;
        if(month < 10)
            monthOfYear = "0" + monthOfYear;

        taskDueDate.setText(day + "/" + monthOfYear + "/" + year);
    }

    private void sendPostTask () {
        String result;
        String register = lastActivityBundle.getString("register");

        // commit these changes
        String taskStatus = ((SpinnerValues) taskStatusSpinner.getSelectedItem()).getStatus();

        task.setTitle(taskTitle.getText().toString());
        task.setDescription(taskDesc.getText().toString());
        task.setBRDue_date(taskDueDate.getText().toString() + " " + taskDueTime.getText().toString());
        task.setStatus(Integer.valueOf(taskStatus));

        if(register != null)
            result = task.INSERT();
        else
            result = task.UPDATE();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_add);

        lastActivityBundle = getIntent().getExtras();
        String id = lastActivityBundle.getString("id");

        FloatingActionButton fab = findViewById(R.id.fab);
        taskStatusSpinner = findViewById(R.id.SpinnerStatusTask);
        spinnerSetAdapter(taskStatusSpinner);

        taskTitle = findViewById(R.id.TitleTask);
        taskDesc = findViewById(R.id.DescriptionTask);
        taskDueDate = findViewById(R.id.txtViewDueDateTask);
        taskDueTime = findViewById(R.id.txtViewDueTimeTask);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        if(id != null) {
            getSupportActionBar().setTitle("Editar");
            fab.setImageResource(R.drawable.ic_edit);

            task = Task.SELECT(Integer.parseInt(id));

            taskTitle.setText(task.getTitle());
            taskDesc.setText(task.getDescription());
            taskDueDate.setText(dateFormat.format(task.getDue_date()));
            taskDueTime.setText(task.getDue_timeString());
            taskStatusSpinner.setSelection(task.getStatus());
        } else {
            getSupportActionBar().setTitle("Cadastrar");

            taskDueDate.setText(dateFormat.format(new Date()));
            dateFormat = new SimpleDateFormat("HH:mm");
            taskDueTime.setText(dateFormat.format(new Date()));
        }

        taskDueDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        taskDueTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message;

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date date = null;

                try {
                    date = dateFormat.parse(taskDueDate.getText().toString() + " " + taskDueTime.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(id == null && date.before(new Date())) // only works for register action
                    Toast.makeText(FormActivity.this, "Data de vencimento inválida", Toast.LENGTH_LONG).show();
                else if(taskTitle.getText().toString().trim().length() == 0)
                    Toast.makeText(FormActivity.this, "Título da tarefa necessário", Toast.LENGTH_LONG).show();
                else {
                    sendPostTask();

                    Intent intent = new Intent(FormActivity.this, MainActivity.class);
                    startActivity(intent);

                    finish();
                }
            }
        });
    }
}