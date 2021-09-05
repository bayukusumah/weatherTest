package com.code.weathertest.API;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiData {
    private static final String URL = "https://api.openweathermap.org/data/2.5/";
    private static Retrofit retrofit;
    public static <S> S createService(Class<S> sClass, Context context){
        try{
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return retrofit.create(sClass);
    }

}
