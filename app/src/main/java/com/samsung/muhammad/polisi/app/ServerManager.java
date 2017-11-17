package com.samsung.muhammad.polisi.app;

import android.util.Log;

import com.samsung.muhammad.polisi.util.FileUtil;
import com.samsung.muhammad.polisi.util.Server;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dody on 10/11/17.
 */

public class ServerManager {

    private static ServerManager instance;

    private ServerAPI serverApi = null;

    private boolean enableLog = true;

    public static ServerManager getInstance() {
        if (instance == null) {
            instance = new ServerManager();
        }

        return instance;
    }

    private ServerManager() {
        createService();
    }

    private void createService() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (enableLog)
                    Log.e("Retrofit", message);
            }
        });

        loggingInterceptor.setLevel(enableLog ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        // http caching
        int cacheSize = 10 * 1024 * 1024; // 10MB cache
        File cacheDir = new File(FileUtil.getInstance().CACHE_FILE_PATH, "cache");
        Cache cache = new Cache(cacheDir, cacheSize);

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .cache(cache)
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Server.URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        serverApi = retrofit.create(ServerAPI.class);
    }

    public ServerAPI getService() {
        return serverApi;
    }

}
