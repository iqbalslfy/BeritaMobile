package com.samsung.muhammad.polisi.activity;

import android.app.ProgressDialog;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orhanobut.hawk.Hawk;
import com.samsung.muhammad.polisi.R;
import com.samsung.muhammad.polisi.config.RequestHandler;
import com.samsung.muhammad.polisi.config.SettingDB;
import com.samsung.muhammad.polisi.responses.LoginResponse;

import com.orhanobut.hawk.Hawk;
import com.samsung.muhammad.polisi.responses.LoginResponse;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfilActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgprofil;
    private TextView nama,alamat,polsek,no,urlImg;
    private String URL;
    private String JSON_STRING;
    private LoginResponse user;
    private EditText editNamaPolres,editAlamatPolres,editNoHp,editPassword;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profil");

        imgprofil = (ImageView)findViewById(R.id.imgProfilPolsek);
       // nama = (TextView)findViewById(R.id.txt_ic_name);
        //alamat = (TextView)findViewById(R.id.txt_ic_alamat);
        //polsek = (TextView)findViewById(R.id.txt_ic_polsek);
        //no = (TextView)findViewById(R.id.txt_ic_nohp);
        urlImg = (TextView)findViewById(R.id.urlImageP);

        editNamaPolres=(EditText) findViewById(R.id.editNamaPolres);
        editAlamatPolres=(EditText) findViewById(R.id.editAlamatPolres);
        editNoHp=(EditText) findViewById(R.id.editNoHp);
        editPassword=(EditText) findViewById(R.id.editPassword);

        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);


        Hawk.init(this).build();
        this.user = Hawk.get("user");
        //Toast.makeText(getApplicationContext(), user.getKd_polsek(), Toast.LENGTH_SHORT).show();

        getJSON();
    }
    public void onClick(View view) {

            InsertToDatabase();


    }
    private void InsertToDatabase(){
//        final String kd_kr = kd_kriteria.getText().toString().trim();
        final String nama_polres = editNamaPolres.getText().toString().trim();
        final String alamat_polres = editAlamatPolres.getText().toString().trim();
        final String no_hp = editNoHp.getText().toString().trim();
        final String password = editPassword.getText().toString().trim();





        class InsertToDatabase extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ProfilActivity.this, s , Toast.LENGTH_SHORT).show();
                //clear();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(ProfilActivity.this, "Menambahkan", "Tunggu",false,false);

            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(SettingDB.KEY_NAMA_POLRES, nama_polres);
                params.put(SettingDB.KEY_ALAMAT_POLRES, alamat_polres);
                params.put(SettingDB.KEY_NO_HP, no_hp);
                params.put(SettingDB.KEY_PASSWORD, password);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(SettingDB.URL_UPDATE_PROFIL+"?polres="+user.getKd_polsek(), params);

                return res;
            }
        }

        InsertToDatabase toDatabase = new InsertToDatabase();
        toDatabase.execute();
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ProfilActivity.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                LihatData(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(SettingDB.URL_PROFIL_POLSEK+"?kd_polsek="+user.getKd_polsek());
                return s;
            }
        }

        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void LihatData(String json){

        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(SettingDB.TAG_JSON_ARRAY);


            JSONObject obj = result.getJSONObject(0);


            String h1 = obj.getString(SettingDB.TAG_IMAGE);
            String h2 = obj.getString(SettingDB.TAG_ALAMAT);
            String h3 = obj.getString(SettingDB.TAG_POLSEK);
            String h4 = obj.getString(SettingDB.TAG_NO_TELP);
            String h5 = obj.getString(SettingDB.TAG_NAMA);
            String h6 = obj.getString(SettingDB.TAG_PASSWORD);


           /* nama.setText(h5);
            alamat.setText(h2);
            polsek.setText(h3);
            no.setText(h4);*/
            urlImg.setText(h1);

            editNamaPolres.setText(h5);
            editAlamatPolres.setText(h2);
            editNoHp.setText(h4);
            editPassword.setText(h6);

            URL = String.valueOf(urlImg.getText());
            Glide.with(this).load(URL)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgprofil);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
