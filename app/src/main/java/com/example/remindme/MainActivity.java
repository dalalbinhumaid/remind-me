package com.example.remindme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    // public static final String CHANNEL_1_ID = "channel1";
    // public static final String CHANNEL_2_ID = "channel2";

    TaskAdapter adapter;
    ArrayList<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_RemindMe);
        setContentView(R.layout.activity_main);
        tasks = new ArrayList<>(); // It will hold all the added tasks

        // When the user clicks the FAB it will open the SecondaryActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task addTask;
                Intent i = new Intent(MainActivity.this, SecondaryActivity.class);
                startActivityForResult(i, 1);
            }
        });

        // Set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.tasksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(this, tasks); // The adapter will manage the data onto the RecyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView); // For gestures and swipes
    }

    /*
     * When the user swipes right it will trigger the code the onSwiped method,
     * which will remove an item from the list and notify the adapter to fix the view accordingly.
     */
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            tasks.remove(viewHolder.getAdapterPosition());
            adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }
    };

    /*
     * When the user clicks the add task button it will send the Intent here
     *  we can then get the data and create a Task object and add it to the list
     *  then, we notify the adapter to add the task onto the ViewHolder.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String title = data.getStringExtra("title");
                int importance = data.getIntExtra("importance", -2);
                String date = data.getStringExtra("date");
                String time = data.getStringExtra("time");
                tasks.add(new Task(title, false, importance, time, date));
                adapter.notifyDataSetChanged();
            }
        }
    }

    /*
     * IMPLEMENT THE NOTIFICATION CHANNELS HERE
     */
}



