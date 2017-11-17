package com.samsung.muhammad.polisi.activity;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.samsung.muhammad.polisi.R;
import com.samsung.muhammad.polisi.apihelper.BaseApiService;
import com.samsung.muhammad.polisi.apihelper.*;
import com.samsung.muhammad.polisi.networks.RetrofitApi;
import com.samsung.muhammad.polisi.responses.LoginResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText user, pass;
    ProgressDialog loading;
    Context mContext;
    BaseApiService mApiService;
    String text1, text2;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        Hawk.init(this).build();

        user = (EditText) findViewById(R.id.txtusername);
        pass = (EditText) findViewById(R.id.txtpassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginMasuk();
            }
        });
    }

    public void LoginMasuk() {
        text1 = user.getText().toString();
        text2 = pass.getText().toString();
        if ((text1.matches("") || text2.matches(""))) {
            Toast.makeText(this, "Isikan Username dan Password", Toast.LENGTH_SHORT).show();
        } else {
            requestlogin();
//            Toast.makeText(this, "Login Gagal /Username Password Salah", Toast.LENGTH_SHORT).show();

        }
    }

    private void requestlogin() {
        Call<LoginResponse> loginCall;
        loginCall = RetrofitApi.getInstance().getApiService().login(user.getText().toString(), pass.getText().toString());
        loginCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Hawk.put("user", response.body());
                    //startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    startActivity(new Intent(LoginActivity.this, DepanActivity.class));
                }else
                    Toast.makeText(LoginActivity.this, "Username/Password salah ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Kesalahan Koneksi, Periksa Jaringan Internet Anda", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
