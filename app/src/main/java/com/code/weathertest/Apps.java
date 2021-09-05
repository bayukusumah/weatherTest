package com.code.weathertest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.code.weathertest.API.Api;
import com.code.weathertest.API.ApiData;

public class Apps extends Application {
    private Api apiService =null;
    private static Apps apps;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        apps = this;
        sharedPreferences = this.getSharedPreferences("NAMAfILE",0);
    }
    public static Apps getAppInstance(){
        return apps;
    }
    public Api getApiService(Context context){
        if(apiService == null)
            this.apiService = new ApiData().createService(Api.class,context);
        return apiService;
    }
    public void setCity(String name){
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("CITY", name);
        edit.apply();
    }
    public String getCity(){
        return sharedPreferences.getString("CITY","");
    }
}
