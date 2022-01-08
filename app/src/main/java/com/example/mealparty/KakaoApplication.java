package com.example.mealparty;

import android.app.Activity;
import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    private static volatile KakaoApplication obj = null;
    private static volatile Activity currentActivity = null;
    @Override
    public void onCreate() {
        super.onCreate();
        obj = this;

        String kakao_app_key = getResources().getString(R.string.kakao_app_key);
        KakaoSdk.init(this, kakao_app_key);
    }

    public static KakaoApplication getKakaoApplicationContext(){
        return obj;
    }

    public static Activity getCurrentActivity(){
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity){
        KakaoApplication.currentActivity = currentActivity;
    }
}
