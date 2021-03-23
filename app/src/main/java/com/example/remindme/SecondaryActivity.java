package com.example.remindme;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

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
import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SecondaryActivity extends AppCompatActivity {
    TimePickerDialog timePickerDialog;
    TextView dateText, timeText;
    ImageView dateButton, timeButton;
    int importance;
    String title, time, date;

    //current date and time information
    final Calendar cal = Calendar.getInstance();
    int YEAR = cal.get(Calendar.YEAR);
    int MONTH = cal.get(Calendar.MONTH);
    int DAY = cal.get(Calendar.DAY_OF_MONTH);
    int HOUR = cal.get(Calendar.HOUR);
    int MINUTE = cal.get(Calendar.MINUTE);
    int notification_hour;
    int notification_minutes;


    //notification
    private int notificationId=1;

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
            public void onClick(View v) { setTime(); }
        });

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case -1:
                        System.out.println("Choices cleared!");
                        break;
                    case R.id.radioBtnHigh:
                        importance = 0;
                        break;
                    case R.id.radioBtnLow:
                        importance = 2;
                        break;
                    default:
                        break;
                }
            }
        });

        Button button = findViewById(R.id.addTaskButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText task = findViewById(R.id.taskTitleInput);
                TimePickerDialog timePicker= timePickerDialog;
                title = task.getText().toString();
                task.setText("");
                Intent result = new Intent(SecondaryActivity.this, AlarmReceiver.class);
                result.putExtra("notificationId", notificationId);
                result.putExtra("title", title);
                result.putExtra("importance", importance);
                result.putExtra("date", date);
                result.putExtra("time", time);

                PendingIntent alarmIntent= PendingIntent.getBroadcast(
                        SecondaryActivity.this, 0, result, PendingIntent.FLAG_CANCEL_CURRENT
                );
                AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
                Calendar startTime= Calendar.getInstance();

                startTime.set(Calendar.HOUR_OF_DAY,  notification_hour);
                startTime.set(Calendar.MINUTE, notification_minutes);
                startTime.set(Calendar.SECOND, 0);
                long alarmStartTime= startTime.getTimeInMillis();
                        //set alarm
                        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime,alarmIntent);
                    setResult(RESULT_OK, result); finish();
            }
        });

        Button button_cancel = findViewById(R.id.CancelButton);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override

        public void onClick(View v) {
                finish();
            }
    });
    }

    void setDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(SecondaryActivity.this, R.style.Theme_RemindMe, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar dateCal = Calendar.getInstance();
                dateCal.set(cal.YEAR, year);
                dateCal.set(cal.MONTH, month);
                dateCal.set(cal.DAY_OF_MONTH, dayOfMonth);

                date = DateFormat.format("MMM d, yyyy", dateCal).toString();

                dateText.setText(date);

            }
        }, YEAR, MONTH, DAY);

        datePickerDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        datePickerDialog.getWindow().setGravity(Gravity.CENTER);
        datePickerDialog.show();
    }

    void setTime() {
         timePickerDialog = new TimePickerDialog(SecondaryActivity.this, R.style.Theme_RemindMe, new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Calendar timeCal = Calendar.getInstance();
                timeCal.set(Calendar.HOUR, hour);
                timeCal.set(Calendar.MINUTE, minute);
                notification_hour=hour;
                notification_minutes=minute;

                time = DateFormat.format("h:mm a", timeCal).toString();
                timeText.setText(time);
            }
        }, HOUR, MINUTE, true);

        timePickerDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        timePickerDialog.getWindow().setGravity(Gravity.CENTER);
        timePickerDialog.show();
    }
}
