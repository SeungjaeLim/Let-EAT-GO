package com.example.mealparty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mealparty.placeholder.PlaceholderContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A fragment representing a list of Items.
 */
public class PartyFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    public static RequestQueue requestQueue;
    ArrayList jidList = new ArrayList();
    int jidcnt = 0;
    Context ct;
    private static final String TAG = "MAIN";
    ArrayList<String> list = new ArrayList<>();
    MyItemRecyclerViewAdapter adapter = new MyItemRecyclerViewAdapter(list);

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
        System.out.println(list);
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(adapter);
        return view;
    }

    private void Show_All_Party()
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
                        list.add(jsonArray.getJSONObject(i).getString("Name"));
                        JoinedList.add(jsonArray.getJSONObject(i).getInt("Joined"));
                        MAXjoinList.add(jsonArray.getJSONObject(i).getInt("MAXjoin"));
                        timeList.add(jsonArray.getJSONObject(i).getString("time"));
                        hostList.add(jsonArray.getJSONObject(i).getString("host"));
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

    private void Create_Party(String userid, String category, String name, int maxjoin, String time)
    {
        String url = "http://192.249.5.55:80";
        url = url + "/api/partys/create/"+userid+"/"+category+"/"+name+"/"+maxjoin+"/"+time;
        System.out.println(url);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    jidList.add(jsonObject.getString("id"));
                    jidcnt++;
                } catch (JSONException e) {
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
        System.out.println("Send Request Create");
    }

    private void Delete_Party(String userid, String jobid)
    {
        String url = "http://192.249.5.55:80";
        url = url + "/api/partys/delete/"+userid+"/"+jobid;
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
        System.out.println("Send Request Delete");
    }

}