package com.samsung.muhammad.polisi.networks;

import com.samsung.muhammad.polisi.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by blegoh on 15/07/17.
 */
public interface ApiService {

    @POST("login.php")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("username") String email,
                              @Field("password") String password
    );

}


