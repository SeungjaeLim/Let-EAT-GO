package com.example.mealparty;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealparty.ui.gallery.GalleryFragment;
import com.example.mealparty.ui.home.HomeFragment;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;



import com.example.mealparty.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kakao.auth.authorization.accesstoken.AccessToken;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApi;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.AccessTokenInfo;
import com.kakao.sdk.user.model.User;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    ActionBarDrawerToggle dtToggle;

    //
    private ViewPager2 viewPager2;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //StrictMode.enableDefaults();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //getHashKey();
        //setSupportActionBar(binding.appBarMain.toolbar);
        /*binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        //DrawerLayout drawer = binding.drawerLayout;
        //NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();*/
        //
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);

        ///
        /*navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_home:
                        Toast.makeText(getApplicationContext(), "SelectedItem 1", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_gallery:
                        Toast.makeText(getApplicationContext(), "SelectedItem 2", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        break;
                }
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });*/

        ////

        //navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);



        //ArrayList<Fragment> fragments = new ArrayList<>();
        //fragments.clear();
        fragments.add(MenuCrawling.newInstance("01","01"));
        fragments.add(PartyFragment.newInstance(1));
        //fragments.add(MyPageFragment.newInstance("10","10"));
        fragments.add(AfterLoginFragment.newInstance("10","10"));






        //
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = (ViewPager2) findViewById(R.id.viewPager2);
        pageAdapter = new PageAdapter(this, fragments);
        viewPager2.setAdapter(pageAdapter);
        final List<String> tabElement = Arrays.asList("메뉴","파티","마이페이지");
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy(){
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position){
                TextView textView = new TextView(MainActivity.this);
                textView.setText(tabElement.get(position));
                tab.setCustomView(textView);
            }
        }).attach();

        /*Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken != null) {
                    Log.i("user", oAuthToken.getAccessToken() + " " + oAuthToken.getRefreshToken());
                } if (throwable != null) {
                    // TBD
                    Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());
                }
                updateKakaoLoginUi();

                return null;
            }
        };*/



        //Button login_button = (Button) binding.getRoot().findViewById(R.id.button_login2);

        /*binding.buttonLogin2.setOnClickListener(new OnClickListener(){

            public void onClick(View view){
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)){
                    //카카오톡이 있을경우
                    UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this, callback);
                }else{
                    UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this,callback);
                }
            }
        });*/
        //binding.buttonLogin.setOnClickListener(this::onClick);
        //binding.appBarMain.contentMain.buttonLogin2.setOnClickListener(this::onClick);
        updateKakaoLoginUi();


    }



    public void onClick(View v){
        Snackbar.make(v, "Replace", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)){
            //카카오톡이 있을경우
            UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this, callback);
        }else{
            UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this,callback);
        }
    }
    Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
        @Override
        public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
            if (oAuthToken != null) {
                Log.i("user", oAuthToken.getAccessToken() + " " + oAuthToken.getRefreshToken());
            } if (throwable != null) {
                // TBD
                Log.w(TAG, "invoke: " + throwable.getLocalizedMessage());
            }
            updateKakaoLoginUi();

            return null;
        }
    };


    private final static String TAG = "유저";
    private User currentUser;

    /*public boolean onNavigationItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.nav_home:
                Toast.makeText(this,"nav home", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_gallery:
                Toast.makeText(this,"nav gallery", Toast.LENGTH_LONG).show();
                break;
        }
        return false;
    }*/







    public void updateKakaoLoginUi(){
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user !=null){
                    Log.i(TAG, "id" + user.getId());
                    Log.i(TAG, "invoke: nickname=" + user.getKakaoAccount().getProfile().getNickname());
                    Log.i(TAG, "userimage" + user.getKakaoAccount().getProfile().getProfileImageUrl());
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

    ArrayList<Fragment> fragments = new ArrayList<>();
    PageAdapter pageAdapter;

    int BEFORE_LOGIN = 0;
    int AFTER_LOGIN = 1;

    public void onFragmentChange(int fragmentNum) {
        if(fragmentNum == BEFORE_LOGIN){
            //fragments.add(MyPageFragment.newInstance("10","10"));
            fragments.set(2, MyPageFragment.newInstance("10","10"));

        } else if (fragmentNum == AFTER_LOGIN){
            fragments.set(2, AfterLoginFragment.newInstance("10","10"));
        }
        viewPager2 = (ViewPager2) findViewById(R.id.viewPager2);
        pageAdapter = new PageAdapter(this, fragments);
        viewPager2.setAdapter(pageAdapter);


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

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);

    }

    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);

        }catch(PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        if (packageInfo == null){
            Log.e("HashKey", "HashKey:null");
        }

        for(Signature signature : packageInfo.signatures){
            try{
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("HashKey", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e){
                Log.e("HashKey", "HashKey Error. signature=" + signature, e);
            }

        }
    }
}