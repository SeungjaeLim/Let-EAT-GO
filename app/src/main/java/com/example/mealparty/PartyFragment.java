package com.example.mealparty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.fonts.SystemFonts;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mealparty.placeholder.PlaceholderContent;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/**
 * A fragment representing a list of Items.
 */
public class PartyFragment extends Fragment {

    static String userid;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    public static RequestQueue requestQueue;
    ArrayList jidList = new ArrayList();
    int jidcnt = 0;
    static Context ct;
    private static final String TAG = "MAIN";
    static ArrayList<Party_Item> list = new ArrayList<>();
    static MyItemRecyclerViewAdapter adapter = new MyItemRecyclerViewAdapter(list);

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PartyFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PartyFragment newInstance(int columnCount) {
        PartyFragment fragment = new PartyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        if(requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_party_list, container, false);
        // Set the adapter
        Show_All_Party();
        ct = getActivity();
        System.out.println(list);
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(adapter);
        updateKakaoLoginUi();

        //create button
        Button partyCreateButton = (Button) view.findViewById(R.id.create_button);
        partyCreateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(),CreatePartyActivity.class);
                startActivity(intent);
            }

        });
        return view;
    }

    public static void Show_All_Party()
    {
        String url = "http://192.249.18.138:80";
        url = url + "/api/partys/show/all";

        Map<String, ArrayList> partyMap = new HashMap<>();
        ArrayList idList = new ArrayList();
        ArrayList CategoryList = new ArrayList();
        ArrayList NameList = new ArrayList();
        ArrayList JoinedList = new ArrayList();
        ArrayList MAXjoinList = new ArrayList();
        ArrayList timeList = new ArrayList();
        ArrayList hostList = new ArrayList();

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
                        String date_ = timeList.get(i).toString().substring(5,10);
                        String time_ = timeList.get(i).toString().substring(11,16);
                        String formated_time = date_.substring(0,2) + "월 " + date_.substring(3,5) + "일 " + time_.substring(0,2) + "시 " + time_.substring(3,5)+ "분";
                        Party_Item partyelem = new Party_Item(JoinedList.get(i) + "/" + MAXjoinList.get(i),formated_time,NameList.get(i).toString(), idList.get(i).toString());
                        list.add(partyelem);
                    }
                    partyMap.put("id",idList);
                    partyMap.put("Category",CategoryList);
                    partyMap.put("Name",NameList);
                    partyMap.put("Join",JoinedList);
                    partyMap.put("MAXjoin",MAXjoinList);
                    partyMap.put("time",timeList);
                    partyMap.put("host",hostList);
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

    public static void Participate_Party(String jobid)
    {
        if(userid == null)
        {
            System.out.println("null user ID");
            return;
        }
        String url = "http://192.249.18.138:80";
        url = url + "/api/partys/participate/"+userid+"/"+jobid;
        System.out.println(url);
        String Errmsg1 = "already joined";
        String Errmsg2 = "err-FULL PARTY";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                System.out.println(response);

                if(response.equals(Errmsg1))
                {
                    Toast.makeText(ct, "이미 참여하였습니다.", Toast.LENGTH_SHORT).show();
                }
                else if(response.equals(Errmsg2))
                {
                    Toast.makeText(ct, "꽉 찼습니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ct, "파티에 참여하였습니다.", Toast.LENGTH_SHORT).show();
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