package com.samsung.muhammad.polisi.networks;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.samsung.muhammad.polisi.apihelper.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by blegoh on 15/07/17.
 */
public class RetrofitApi {
    private ApiService mApiService;
    private static RetrofitApi instance = null;
    private Retrofit retrofit;
    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();
    private String token = "";


    public static RetrofitApi getInstance() {
        if (instance == null)
            instance = new RetrofitApi();

        return instance;
    }

    private RetrofitApi() {
        retrofit = new Retrofit.Builder().baseUrl(http.URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getOkHttp().build())
                .build();
        mApiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService(String token) {
        this.token = token;
        return mApiService;
    }

    public ApiService getApiService() {
        return mApiService;
    }

    private OkHttpClient.Builder getOkHttp() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("Network", message);
            }
        });
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .build();
                return chain.proceed(request);
            }
        });


        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(interceptor);

        return httpClient;
    }
}
