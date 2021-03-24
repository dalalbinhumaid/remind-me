package com.example.remindme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;

public class task_deatils extends AppCompatActivity {

    TextView taskTitle, date, time ,imoprtance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_deatils);

        taskTitle.findViewById(R.id.taskTitle);
        date.findViewById(R.id.date);
        time.findViewById(R.id.taskTime);
        imoprtance.findViewById(R.id.level);





    }


}