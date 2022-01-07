package com.example.mealparty;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.example.mealparty.ui.gallery.GalleryFragment;
import com.example.mealparty.ui.home.HomeFragment;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mealparty.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    //
    private ViewPager2 viewPager2;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //StrictMode.enableDefaults();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }


        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawer.closeDrawers();

                int id = item.getItemId();
                switch(id){
                    case R.id.nav_home:
                        if (getSupportFragmentManager().findFragmentByTag("Home") != null) {
                            getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag("Home")).commit();
                        } else {
                            getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment_content_main, new HomeFragment(), "Home").commit();
                        }
                        //보이는거 처리
                        if(getSupportFragmentManager().findFragmentByTag("Gallery") !=null){
                            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("Gallery")).commit();
                        }
                        return true;
                    case R.id.nav_gallery:
                        if (getSupportFragmentManager().findFragmentByTag("Gallery") != null) {
                            getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag("Gallery")).commit();
                        } else {
                            getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment_content_main, new GalleryFragment(), "Gallery").commit();
                        }
                        //보이는거 처리
                        if(getSupportFragmentManager().findFragmentByTag("Home") !=null){
                            getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("Home")).commit();
                        }
                        return true;
                }
                return false;
            }
        });



        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(MenuCrawling.newInstance("01","01"));
        fragments.add(PartyFragment.newInstance(1));

        //
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = (ViewPager2) findViewById(R.id.viewPager2);
        PageAdapter pageAdapter = new PageAdapter(this, fragments);
        viewPager2.setAdapter(pageAdapter);
        final List<String> tabElement = Arrays.asList("메뉴","파티");
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy(){
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position){
                TextView textView = new TextView(MainActivity.this);
                textView.setText(tabElement.get(position));
                tab.setCustomView(textView);
            }
        }).attach();

        ///login

        Button LoginButton = findViewById(R.id.button_login);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //Intent login = new Intent(Intent.)
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}