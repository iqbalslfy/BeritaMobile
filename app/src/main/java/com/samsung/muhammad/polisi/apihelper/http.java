package com.samsung.muhammad.polisi.apihelper;

import retrofit2.Retrofit;

/**
 * Created by muhammad on 18/09/17.
 */

public class http {
    public static final String URL_API = "http://detikhost.com/polres_jembrana/sistem/coba/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(URL_API).create(BaseApiService.class);
    }

}
