package com.samsung.muhammad.polisi.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orhanobut.hawk.Hawk;
import com.samsung.muhammad.polisi.R;
import com.samsung.muhammad.polisi.config.RequestHandler;
import com.samsung.muhammad.polisi.config.SettingDB;
import com.samsung.muhammad.polisi.responses.LoginResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InfoActivity extends AppCompatActivity {

    private ImageView imgprofil;
    private TextView judul,isi,polsek,no,urlImg;
    private String URL;
    private String JSON_STRING;
    private LoginResponse user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("POLDA SUMUT");

        imgprofil = (ImageView)findViewById(R.id.imgProfilPolsek);
        judul = (TextView)findViewById(R.id.judul);
        isi = (TextView)findViewById(R.id.isi);
       // polsek = (TextView)findViewById(R.id.txt_ic_polsek);
       // no = (TextView)findViewById(R.id.txt_ic_nohp);
        urlImg = (TextView)findViewById(R.id.urlImageP);

        Hawk.init(this).build();
        this.user = Hawk.get("user");

        getJSON();
    }


    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(InfoActivity.this,"Mengambil Data","Mohon Tunggu...",false,false);
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
                String s = rh.sendGetRequest(SettingDB.URL_INFO);
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


            String h1 = obj.getString(SettingDB.TAG_FOTO_KAPOLDA);
            String h2 = obj.getString(SettingDB.TAG_JUDUL_ABOUTUS);
            String h3 = obj.getString(SettingDB.TAG_ISI_ABOUT);


            judul.setText(h2);
            isi.setText(h3);
            urlImg.setText(h1);

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
