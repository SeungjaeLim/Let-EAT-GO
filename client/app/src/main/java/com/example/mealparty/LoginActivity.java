package com.example.mealparty;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

public class LoginActivity extends AppCompatActivity {
    private final static String TAG = "유저";
    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Context mContext = this;

        ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

        Button loginButton = (Button) findViewById(R.id.button_login_main);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(mContext)){
                    //카카오톡이 있을경우
                    UserApiClient.getInstance().loginWithKakaoTalk(mContext, callback);


                }else{
                    UserApiClient.getInstance().loginWithKakaoAccount(mContext,callback);

                }
                finish();
            }
        });

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

    public void updateKakaoLoginUi(){
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user !=null){
                    Log.i(TAG, "id" + user.getId());
                    Log.i(TAG, "invoke: nickname=" + user.getKakaoAccount().getProfile().getNickname());
                    Log.i(TAG, "userimage" + user.getKakaoAccount().getProfile().getProfileImageUrl());

                    //Intent intent = new Intent(getActivity(), AfterLogin.class);
                    //startActivity(intent);


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