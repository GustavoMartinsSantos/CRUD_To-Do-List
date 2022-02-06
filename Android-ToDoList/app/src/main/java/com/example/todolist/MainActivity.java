package com.example.todolist;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.todolist.classes.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Task> tasks;
    RecyclerView RecyclerViewTasks;
    RecyclerViewAdapter RecyclerViewAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    SearchView searchView;
    Spinner spinnerStatusSearch;

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

        class RecyclerViewHolder extends RecyclerView.ViewHolder {
            public TextView taskTitle;
            public TextView taskDueDate;
            public CheckBox taskStatus;

            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);

                taskTitle = itemView.findViewById(R.id.txtViewTaskTitle);
                taskDueDate = itemView.findViewById(R.id.txtViewTaskDueDate);
                taskStatus = itemView.findViewById(R.id.checkBoxTaskStatus);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, FormActivity.class);
                        intent.putExtra("id", String.valueOf(tasks.get(getAdapterPosition()).getID()));
                        startActivity(intent);
                    }
                });

                taskStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Task task = tasks.get(getAdapterPosition());

                        if(task.getStatus() == 1 || task.getStatus() == 2) // if doing and done
                            task.setStatus(0); // to-do
                        else if(task.getStatus() == 0 || task.getStatus() == 3) // if to-do and late
                            task.setStatus(2); // done

                        task.UPDATE();

                        refreshTasks(
                                ((FormActivity.SpinnerValues) spinnerStatusSearch.getSelectedItem()).getStatus()
                        );
                    }
                });

                taskStatus.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Task task = tasks.get(getAdapterPosition());

                        if(task.getStatus() == 1) // if holding doing icon
                            task.setStatus(0); // to-do
                        else // changing task to doing
                            task.setStatus(1);

                        task.UPDATE();

                        refreshTasks(
                                ((FormActivity.SpinnerValues) spinnerStatusSearch.getSelectedItem()).getStatus()
                        );

                        return true;
                    }
                });
            }
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, parent, false);

            return new RecyclerViewHolder(view);
        }

        @Override // this method populates the recycler view when SELECTing
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
            Task task = tasks.get(position);

            holder.taskTitle.setText(task.getTitle());
            holder.taskDueDate.setText(task.getBRDue_dateTime());

            if(task.getStatus() == 0) // to-do tasks
                holder.taskStatus.setButtonDrawable(R.drawable.ic_unchecked_checkbox);
            else if(task.getStatus() == 1)
                holder.taskStatus.setButtonDrawable(R.drawable.ic_doing_checkbox);
            else if(task.getStatus() == 2)
                holder.taskStatus.setButtonDrawable(R.drawable.ic_checked_checkbox);
            else { // late tasks
                holder.taskTitle.setTextColor(Color.parseColor("#F40303"));
                holder.taskStatus.setButtonDrawable(R.drawable.ic_unchecked_checkbox);
            }
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder, @NonNull ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Task task = tasks.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Deletar Tarefa");
                builder.setMessage("Deseja remover " + task.getTitle() + "?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override // delete this task from the database
                    public void onClick(DialogInterface dialog, int which) {
                        tasks.get(position).DELETE();

                        tasks.remove(position);
                        RecyclerViewAdapter.notifyItemRemoved(position);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RecyclerViewAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    }
                });

                builder.show();
        }

    };

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        searchView = (SearchView) menu.findItem(R.id.searchViewTasks).getActionView();
        searchView.setQueryHint("Procurar tarefas...");
        spinnerStatusSearch = (Spinner) menu.findItem(R.id.SpinnerStatusTask).getActionView();

        FormActivity.spinnerSetAdapter(this, spinnerStatusSearch, true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String taskStatus = ((FormActivity.SpinnerValues) spinnerStatusSearch.getSelectedItem()).getStatus();
                tasks = Task.SELECT(newText, taskStatus);

                RecyclerViewTasks.setAdapter(RecyclerViewAdapter);

                return false;
            }
        });

        spinnerStatusSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshTasks(
                        ((FormActivity.SpinnerValues) spinnerStatusSearch.getSelectedItem()).getStatus()
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefreshLayout = findViewById(R.id.swipelayout);
        RecyclerViewTasks = findViewById(R.id.tasksList);
        RecyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewTasks.setHasFixedSize(true);
        RecyclerViewAdapter = new RecyclerViewAdapter();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(RecyclerViewTasks);
        FloatingActionButton fab = findViewById(R.id.fab);

        refreshTasks("");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                intent.putExtra("register", "true");
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTasks(
                        ((FormActivity.SpinnerValues) spinnerStatusSearch.getSelectedItem()).getStatus()
                );

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void refreshTasks (String taskStatus) {
        tasks = Task.SELECT("", taskStatus);

        RecyclerViewTasks.setAdapter(RecyclerViewAdapter);
    }
}