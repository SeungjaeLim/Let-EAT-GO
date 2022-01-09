package com.example.mealparty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuCrawling#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuCrawling extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //

    //

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenuCrawling() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuCrawling.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuCrawling newInstance(String param1, String param2) {
        MenuCrawling fragment = new MenuCrawling();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        ///


        ///
    }
    Intent intent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_menu_crawling, container, false);
        menuData task = new menuData();
        task.execute();

        /*intent = new Intent(getActivity(), PopupActivity.class);
        intent.putExtra("menu", arrayList);

        Button button_kama = (Button) rootView.findViewById(R.id.button_kama);
        Button button_west = (Button) rootView.findViewById(R.id.button_west);
        Button button_east = (Button) rootView.findViewById(R.id.button_east);

        button_kama.setOnClickListener(this);
        button_west.setOnClickListener(this);
        button_east.setOnClickListener(this);*/

        Button button_kama = (Button) rootView.findViewById(R.id.button_kama);
        Button button_west = (Button) rootView.findViewById(R.id.button_west);
        Button button_east = (Button) rootView.findViewById(R.id.button_east);

        button_kama.setOnClickListener(this);
        button_west.setOnClickListener(this);
        button_east.setOnClickListener(this);


        return rootView;


    }
    int PICK_KAMA = 0;
    int PICK_WEST = 1;
    int PICK_EAST = 2;

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.button_kama:
                intent.putExtra("식당",PICK_KAMA);
                startActivity(intent);
                break;
            case R.id.button_west:
                intent.putExtra("식당",PICK_WEST);
                startActivity(intent);
                break;
            case R.id.button_east:
                intent.putExtra("식당",PICK_EAST);
                startActivity(intent);
                break;
        }
    }
    private String trimMenu(String data){
        data = data.replace("&lt;", "<");
        data = data.replace("&gt;", ">");
        data = data.replace("&amp;", "&");
        data = data.replace("<!-- <ul class=\"list-1st\"> --> ", "");
        data = data.replace(" <!-- </ul> -->", "");
        return data;
    }

    ArrayList<ListData> arrayList = new ArrayList<ListData>();

    private class menuData extends AsyncTask<Void, Void, ArrayList<ListData>> {
        ProgressDialog asyncDialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중입니다...");

            // Show dialog
            asyncDialog.show();
            super.onPreExecute();
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected ArrayList<ListData> doInBackground(Void... voids){
            //ArrayList<ListData> arrayList = new ArrayList<ListData>();
            try {
                /*long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String getTime = sdf.format(date);*/

                LocalDate nowDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                //String getTime = nowDate.format(formatter);





                Calendar cal = Calendar.getInstance();
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                // 1=일 요일, 7 = 토 요일

                int count = 9 - dayOfWeek; // 일요일은 반복문 1번, 토요일은 2번, ... , 월요일은 7
                int plusDay = 0;
                if(dayOfWeek == 1){
                    //일요일일떄
                    count = 1;
                }
                while ( count > 0 ) {
                    LocalDate curDate = nowDate.plusDays(plusDay);
                    String getTime = curDate.format(formatter);

                    //KAMA_LUNCH & DINNER CRAWLING
                    String URL_kama = "https://www.kaist.ac.kr/kr/html/campus/053001.html?dvs_cd=fclt&stt_dt=" + getTime;
                    Document document = (Document) Jsoup.connect(URL_kama).get();
                    //Elements doc = document.select("table.table > tbody > tr");
                    Elements doc = document.select("ul.list-1st");
                    int i = 0;
                    String[] menu = new String[2];
                    for (Element e : doc) {
                        menu[i] = e.html();
                        i++;
                    }

                    String[] kama_lunch = menu[0].split("<br>");
                    i = 0;
                    for (String t : kama_lunch) {
                        kama_lunch[i] = t.trim();
                        i++;
                    }
                    String[] kama_dinner = menu[1].split("<br>");
                    i = 0;
                    for (String t : kama_dinner) {
                        kama_dinner[i] = t.trim();
                        i++;
                    }

                    //check
                /*for(String t : kama_dinner){
                    System.out.println("*");
                    System.out.println(t);
                }*/
                    //

                    //WEST_BREAKFAST & LUNCH & DINNER CRAWLING
                    String URL_west = "https://www.kaist.ac.kr/kr/html/campus/053001.html?dvs_cd=west&stt_dt=" + getTime;
                    document = (Document) Jsoup.connect(URL_west).get();
                    Element morning = document.select("table.table > tbody > tr > td").first();
                    doc = document.select("ul.list-1st");

                    i = 0;
                    for (Element e : doc) {
                        menu[i] = e.html();
                        i++;
                    }
                    String morning_tmp = trimMenu(morning.html());

                    String[] west_breakfast = morning_tmp.split("<br>");
                    i = 0;
                    for (String t : west_breakfast) {
                        west_breakfast[i] = t.trim();
                        i++;
                    }
                    String[] west_lunch = menu[0].split("<br>");
                    i = 0;
                    for (String t : west_lunch) {
                        west_lunch[i] = t.trim();
                        i++;
                    }
                    String[] west_dinner = menu[1].split("<br>");
                    i = 0;
                    for (String t : west_dinner) {
                        west_dinner[i] = t.trim();
                        i++;
                    }

                    //EAST_BREAKFAST & LUNCH & DINNER CRAWLING
                    String URL_east = "https://www.kaist.ac.kr/kr/html/campus/053001.html?dvs_cd=east1&stt_dt=" + getTime;
                    document = (Document) Jsoup.connect(URL_east).get();
                    morning = document.select("table.table > tbody > tr > td").first();
                    doc = document.select("ul.list-1st");

                    i = 0;
                    for (Element e : doc) {
                        menu[i] = trimMenu(e.html());
                        i++;
                    }

                    morning_tmp = trimMenu(morning.html());
                    String[] east_breakfast = morning_tmp.split("<br>");
                    i = 0;
                    for (String t : east_breakfast) {
                        east_breakfast[i] = t.trim();
                        i++;
                    }
                    String[] east_lunch = menu[0].split("<br>");
                    i = 0;
                    for (String t : east_lunch) {
                        east_lunch[i] = t.trim();
                        i++;
                    }
                    String[] east_dinner = menu[1].split("<br>");
                    i = 0;
                    for (String t : east_dinner) {
                        east_dinner[i] = t.trim();
                        i++;
                    }

                    arrayList.add(new ListData(getTime, kama_lunch, kama_dinner, west_breakfast, west_lunch, west_dinner, east_breakfast, east_lunch, east_dinner));
                    count = count - 1;
                    plusDay = plusDay + 1;
                } //end of while
            } catch (Exception e){
                e.printStackTrace();
            }
            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<ListData> arrayList){
            // doInBackground 완료 후 실행시킬 코드

            intent = new Intent(getActivity(), PopupActivity.class);
            intent.putExtra("menu", arrayList);

            asyncDialog.dismiss();
            //super.onPostExecute(result);


            //Intent intent = new Intent(getActivity(), PopupActivity.class);
            //intent.putExtra("메뉴", arrayList);
       }

    }
}