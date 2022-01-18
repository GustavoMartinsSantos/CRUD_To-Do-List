package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;

import com.example.todolist.classes.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/*import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;*/

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Task> tasks;

    class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return tasks.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @NonNull
        @Override
        public View getView(int position, View view, @NonNull ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.task_row, null);

            TextView taskTitle = view.findViewById(R.id.txtViewTaskTitle);
            taskTitle.setText(tasks.get(position).getTitle());
            TextView taskDueDate = view.findViewById(R.id.txtViewTaskDueDate);
            taskDueDate.setText(tasks.get(position).getDue_dateString());

            return view;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });

        tasks = Task.SELECT(""); // fazer barra de pesquisa

        ListView listTasks = findViewById(R.id.tasksList);

        ListViewAdapter listAdapter = new ListViewAdapter();
        listTasks.setAdapter(listAdapter);

        listTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView taskTitle = view.findViewById(R.id.txtViewTaskTitle);
                taskTitle.setText(String.valueOf(tasks.get(position).getID()));
            }
        });
    }
}