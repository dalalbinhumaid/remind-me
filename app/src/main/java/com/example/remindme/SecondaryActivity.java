package com.example.remindme;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SecondaryActivity extends AppCompatActivity {
    TextView dateText, timeText;
    ImageView dateButton, timeButton;
    int importance;
    String title, time,date;

    final Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);
    int HOUR = cal.get(Calendar.HOUR);
    int MINUTE = cal.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        dateText = findViewById(R.id.dateTextView);
        dateButton = findViewById(R.id.calendar);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });

        timeText = findViewById(R.id.timeTextView);
        timeButton = findViewById(R.id.time);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            setTime();
            }
        });

        RadioGroup radioGroup= (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId) {
                    case -1:
                        System.out.println ("Choices cleared!");
                        break;
                    case R.id.radioBtnHigh:
                        importance = 0;
                        break;
                    case R.id.radioBtnMid:
                        importance = 1;
                        break;
                    case R.id.radioBtnLow:
                       importance =2;
                        break;
                    default:
                        System.out.println ("??????????");
                        break;
                }
            }
        });
        Button button = findViewById(R.id.addTaskButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText task = findViewById(R.id.taskTitleInput);
                title = task.getText().toString();
                task.setText("");
                Intent result = new Intent();
                result.putExtra("title", title);
                result.putExtra("importance", importance);
                result.putExtra("date", date);
                result.putExtra("time", time);
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }

    void setDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(SecondaryActivity.this, R.style.Theme_RemindMe, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateText.setText(day + "-" + (month + 1) + "-" + year);
                date = day + "-" + (month + 1) + "-" + year;
            }
        }, year, month, day);

        datePickerDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        datePickerDialog.getWindow().setGravity(Gravity.CENTER);
        datePickerDialog.show();
    }

    void setTime(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                SecondaryActivity.this, R.style.Theme_RemindMe, new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Log.i("MainActivity", "onTimeSet: " + hour + minute);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR, hour);
                calendar1.set(Calendar.MINUTE, minute);
                String dateText = DateFormat.format("h:mm a", calendar1).toString();
                timeText.setText(dateText);
                time = dateText;
            }
        }, HOUR, MINUTE, true);
        timePickerDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        timePickerDialog.getWindow().setGravity(Gravity.CENTER);
        timePickerDialog.show();
    }
}
