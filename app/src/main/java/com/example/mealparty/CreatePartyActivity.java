package com.example.mealparty;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class CreatePartyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_create_party);

        String[] categoryItem = new String[]{"카이스트","어은동","궁동","둔산동","기타"};

        Spinner category = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinneritem, categoryItem);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        Button cancelButton = findViewById(R.id.button_cancel);
        Button completeButton = findViewById(R.id.button_complete);

        TextView dateTextView = findViewById(R.id.textView_DatePick);
        TextView timeTextView = findViewById(R.id.textView_TimePick);

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                dateTextView.setText(i + "/" + (i1+1) + "/" + i2);
            }
        }, mYear, mMonth, mDay);

        int hour = c.get(c.HOUR_OF_DAY);
        int minute = c.get(c.MINUTE);
        int second  = c.get(c.SECOND);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //TimePickerDialog timePickerDialog = new TimePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK,,hour,minute, DateFormat.is24HourFormat(this));


        dateTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                datePickerDialog.show();
            }
        });

        timeTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreatePartyActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        timeTextView.setText(i+" : "+i1);
                    }
                },hour,minute,false);
                timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();
            }
        });



        /*category.setOnItemClickListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                TextView.setText(categoryItem[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });*/

    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }*/

    /*@Override
    public void onBackPressed() {
    //백버튼 막기
        return;
    }*/
}