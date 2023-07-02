package com.example.mealparty;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class CreatePartyList extends AppCompatActivity {

    static String userid;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    public static RequestQueue requestQueue;
    static Context ct;
    private static final String TAG = "MAIN";
    static ArrayList<Party_Item> list = new ArrayList<>();
    static CreatePartyListRecyclerViewAdapter adapter = new CreatePartyListRecyclerViewAdapter(list);
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party_list);
        if(requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        swipeRefreshLayout = findViewById(R.id.cre_swiperefreshlayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("refresh create");
                list.clear();
                System.out.println("CLEAR LIST" + list);
                Show_All_Party();
                System.out.println("UPDATE LIST" + list);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        ct = this;
        updateKakaoLoginUi();
        System.out.println(list);
        RecyclerView recyclerView = findViewById(R.id.Cpartylist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();


        recyclerView.setAdapter(adapter);
    }

    public static void Show_All_Party()
    {
        String url = "http://192.249.18.138:80";
        url = url + "/api/mypage/host/"+userid;
        list.clear();

        ArrayList idList = new ArrayList();
        ArrayList CategoryList = new ArrayList();
        ArrayList NameList = new ArrayList();
        ArrayList JoinedList = new ArrayList();
        ArrayList MAXjoinList = new ArrayList();
        ArrayList timeList = new ArrayList();
        ArrayList hostList = new ArrayList();
        ArrayList hostnameList = new ArrayList();
        ArrayList name1List = new ArrayList();
        ArrayList name2List = new ArrayList();
        ArrayList name3List = new ArrayList();

        System.out.println(url);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response);
                    JSONArray jsonArray = new JSONArray(response);
                    System.out.println(jsonArray.getJSONObject(0));
                    for(int i = 0; i<jsonArray.length(); i++)
                    {
                        idList.add(jsonArray.getJSONObject(i).getString("id"));
                        CategoryList.add(jsonArray.getJSONObject(i).getString("Category"));
                        NameList.add(jsonArray.getJSONObject(i).getString("Name"));
                        JoinedList.add(jsonArray.getJSONObject(i).getInt("Joined"));
                        MAXjoinList.add(jsonArray.getJSONObject(i).getInt("MAXjoin"));
                        timeList.add(jsonArray.getJSONObject(i).getString("time"));
                        hostList.add(jsonArray.getJSONObject(i).getString("host"));
                        hostnameList.add(jsonArray.getJSONObject(i).getString("hostname"));
                        if((int) JoinedList.get(i) == 1)
                        {
                            name1List.add("foo");
                            name2List.add("foo");
                            name3List.add("foo");
                        }
                        else if((int) JoinedList.get(i) == 2)
                        {
                            name1List.add(jsonArray.getJSONObject(i).getString("name1"));
                            name2List.add("foo");
                            name3List.add("foo");
                        }
                        else if((int) JoinedList.get(i) == 3)
                        {
                            name1List.add(jsonArray.getJSONObject(i).getString("name1"));
                            name2List.add(jsonArray.getJSONObject(i).getString("name2"));
                            name3List.add("foo");
                        }
                        else if((int) JoinedList.get(i) == 4)
                        {
                            name1List.add(jsonArray.getJSONObject(i).getString("name1"));
                            name2List.add(jsonArray.getJSONObject(i).getString("name2"));
                            name3List.add(jsonArray.getJSONObject(i).getString("name3"));
                        }

                        String date_ = timeList.get(i).toString().substring(5,10);
                        String time_ = timeList.get(i).toString().substring(11,16);
                        String formated_time = date_.substring(0,2) + "월 " + date_.substring(3,5) + "일 " + time_.substring(0,2) + "시 " + time_.substring(3,5)+ "분";
                        Party_Item partyelem = new Party_Item(JoinedList.get(i) + "/" + MAXjoinList.get(i),formated_time,NameList.get(i).toString(), idList.get(i).toString(), (int) JoinedList.get(i), hostnameList.get(i).toString(), name1List.get(i).toString(), name2List.get(i).toString(), name3List.get(i).toString());
                        list.add(partyelem);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e){
                    e.printStackTrace();
                }
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
        System.out.println("Send Request");
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
                    Show_All_Party();

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

    public static void Delete_Party(String jobid)
    {

        String url = "http://192.249.18.138:80";
        url = url + "/api/partys/delete/"+userid+"/"+jobid;
        System.out.println(url);
        String msg1 = "Delete Failed";
        String msg2 = "Delete Successed";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                System.out.println(response);

                if(response.equals(msg1))
                {
                    Toast.makeText(ct, "삭제에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
                else if(response.equals(msg2))
                {
                    Toast.makeText(ct, "파티를 삭제하였습니다.", Toast.LENGTH_SHORT).show();
                }

                list.clear();
                Show_All_Party();

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
        System.out.println("Send Request Participate");
    }

}