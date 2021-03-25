package com.example.remindme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ShowTask extends AppCompatActivity {

    TextView taskTitle, importance, date, time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            setContentView(R.layout.activity_show_task);

            taskTitle = (TextView) findViewById(R.id.taskTitle);
            importance = (TextView) findViewById(R.id.level);
            date = (TextView) findViewById(R.id.date);
            time = (TextView) findViewById(R.id.taskTime);

            // extract the extra-data in the Notification
            String title = extras.getString("titletask");
            int impo = extras.getInt("impo");
            String taskDate = extras.getString("date");
            String taskTime = extras.getString("time");

            switch (impo){
                case 1:
                    importance.setText("High");
                    break;
                case 2:
                    importance.setText("Low");
                    break;
            }

            taskTitle.setText(title);
            date.setText(taskDate);
            time.setText(taskTime);

        }
    }

}