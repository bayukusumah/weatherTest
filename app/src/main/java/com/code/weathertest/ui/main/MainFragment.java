package com.code.weathertest.ui.main;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.code.weathertest.API.Api;
import com.code.weathertest.Apps;
import com.code.weathertest.R;
import com.code.weathertest.databinding.MainFragmentBinding;
import com.code.weathertest.model.constan;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    MainFragmentBinding binding;
    GPSTracker gpsTracker;
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.main_fragment,container,false);
        ArrayList<String> spinnerArray = new ArrayList<String>();
         spinnerArray.add("Choose your city");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(),R.layout.spinner_content);
         gpsTracker = new GPSTracker(requireActivity());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("Choose your city");
        adapter.add("Gdansk");
        adapter.add("Warszawa");
        adapter.add("Krakow");
        adapter.add("Wroclaw");
        adapter.add("Lodz");
        adapter.add("Current GPS position");
        binding.city.setAdapter(adapter);
        binding.city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String tmp =  parent.getSelectedItem().toString();
               if(!tmp.equals("Choose your city") || !tmp.equals("Current GPS position")){
                   _fist(tmp);
                   Apps.getAppInstance().setCity(tmp);
               }
               if(tmp.equals("Current GPS position")){
                   if(gpsTracker.isGPSEnabled){

                      double lat = gpsTracker.latitude;
                      double longs = gpsTracker.longitude;
                      Log.d("tes","te"+lat);
                      _koordinat(lat,longs);
                   }
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        binding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _fist(null);
            }
        });
        _fist(null);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }
    private void _fist(String city){
        if(city == null){
            city = "Gdansk";
        }

        Call<ResponseBody> call = Apps.getAppInstance().getApiService(requireActivity()).city(city, constan.key);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject obj = new JSONObject(response.body().string());
                        Long longDate = Long.valueOf("1630821734");
                        JSONObject _weather = new JSONObject(obj.getJSONArray("weather").get(0).toString());
                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat postFormater = new SimpleDateFormat("EEEE ,MMMM dd");
                        String hasil = postFormater.format(c);
                        binding.hari.setText(hasil);
                        binding.kota.setText(obj.getString("name")+" "+"Poland");

                        binding.humidity.setText(obj.getJSONObject("main").getString("humidity")+" "+"hPa.");
                        binding.derajat.setText(obj.getJSONObject("wind").getString("deg")+"\u00B0");
                        binding.cuaca.setText(_weather.getString("description"));
                        Glide.with(requireActivity()).load(constan.url+_weather.getString("icon")+".png").into(binding.awan);
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public static String convertDate(String dateInMilliseconds,String dateFormat) {
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }
   private void _koordinat(double lat,double longs){
       Call<ResponseBody> call = Apps.getAppInstance().getApiService(requireActivity()).koordinat(lat,longs, constan.key);
       call.enqueue(new Callback<ResponseBody>() {
           @SuppressLint("SetTextI18n")
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               if(response.isSuccessful()){
                   try {
                       JSONObject obj = new JSONObject(response.body().string());
                       Long longDate = Long.valueOf("1630821734");
                       JSONObject _weather = new JSONObject(obj.getJSONArray("weather").get(0).toString());
                       Date c = Calendar.getInstance().getTime();
                       SimpleDateFormat postFormater = new SimpleDateFormat("EEEE ,MMMM dd");
                       String hasil = postFormater.format(c);
                       binding.hari.setText(hasil);
                       binding.kota.setText(obj.getString("name")+" "+"Poland");

                       binding.humidity.setText(obj.getJSONObject("main").getString("humidity")+" "+"hPa.");
                       binding.derajat.setText(obj.getJSONObject("wind").getString("deg")+"\u00B0");
                       binding.cuaca.setText(_weather.getString("description"));
                       Glide.with(requireActivity()).load(constan.url+_weather.getString("icon")+".png").into(binding.awan);
                   } catch (JSONException | IOException e) {
                       e.printStackTrace();
                   }
               }
           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {

           }
       });
   }
}