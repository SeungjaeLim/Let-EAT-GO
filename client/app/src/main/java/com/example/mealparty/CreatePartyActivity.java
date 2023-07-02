package com.example.mealparty;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class CreatePartyActivity extends AppCompatActivity {
    EditText editText;
    SeekBar seekbar;

    int jyear, jmonth, jday, jhour, jminute;
    String jCategory;
    String jName;
    int MAXjoin;
    String userid;
    private static final String TAG = "MAIN";
    public static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateKakaoLoginUi();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_create_party);
        if(requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(this);
        }
        editText = findViewById(R.id.editText);
        seekbar = (SeekBar) findViewById(R.id.seekBar);

        ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

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
                jyear = i;
                jmonth = i1+1;
                jday = i2;

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

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create call
                String _jmonth;
                String _jday;
                String _jhour;
                String _jminute;
                if(jmonth<10)
                {
                    _jmonth = "0" + jmonth;
                }
                else
                {
                    _jmonth = "" + jmonth;
                }
                if(jday<10)
                {
                    _jday = "0" + jday;
                }
                else
                {
                    _jday = "" + jday;
                }
                if(jhour<10)
                {
                    _jhour = "0" + jhour;
                }
                else
                {
                    _jhour = "" + jhour;
                }
                if(jminute<10)
                {
                    _jminute = "0" + jminute;
                }
                else
                {
                    _jminute = "" + jminute;
                }
                MAXjoin = seekbar.getProgress();
                if(MAXjoin <= 1)
                {
                    System.out.println("less than 2 people");
                    Toast.makeText(getApplicationContext(), "모집 인원을 2인 이상으로 설정해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                String jTime = jyear+_jmonth+_jday+_jhour+_jminute+"00";
                jCategory = category.getSelectedItem().toString();
                jName = editText.getText().toString();
                System.out.println(userid);
                System.out.println(jCategory);
                System.out.println(jName);
                System.out.println(MAXjoin);
                System.out.println(jTime);
                Create_Party(jCategory, jName, MAXjoin, jTime);
                PartyFragment.list.clear();
                PartyFragment.Show_All_Party();
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
                        jhour = i;
                        jminute = i1;
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
    public void Create_Party(String category, String name, int maxjoin, String time)
    {
        String url = "http://192.249.18.138:80";
        url = url + "/api/partys/create/"+userid+"/"+category+"/"+name+"/"+maxjoin+"/"+time;
        System.out.println(url);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                return params;
            }
        };
        request.setTag(TAG);
        request.setShouldCache(false);
        requestQueue.add(request);
        System.out.println("Send Request Create");
    }

    public void updateKakaoLoginUi(){
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user !=null){
                    Log.i(TAG, "id" + user.getId());
                    Log.i(TAG, "invoke: nickname=" + user.getKakaoAccount().getProfile().getNickname());
                    Log.i(TAG, "userimage" + user.getKakaoAccount().getProfile().getProfileImageUrl());

                    userid = "" + user.getId();


                    //로그인 정상적으로 되었을 경우 수행하는 코드 적
                }
                if(throwable != null){
                    //로그인 오류
                    Log.w(TAG, "invoke: "+throwable.getLocalizedMessage());
                }
                return null;
            }
        });
    }
}