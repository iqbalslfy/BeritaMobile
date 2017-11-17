package com.samsung.muhammad.polisi.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.samsung.muhammad.polisi.R;
import com.samsung.muhammad.polisi.config.RequestHandler;

import com.samsung.muhammad.polisi.config.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailPengaduan extends AppCompatActivity  {

    private EditText kat,tgl,jdl,ket,lokasi,status,kri,pol;
    private Button update;
    private String idnama;
    private String URL;
    private TextView urlimg,ids;
    private ImageView imgD;
    private Spinner spin_status;

    String [] dialogs = {"Proses", "Selesai"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data_pengaduan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        idnama = intent.getStringExtra(SettingDB.KEY_ID);

        ids = (TextView)findViewById(R.id.idd);
        kat = (EditText)findViewById(R.id.kategorid);
        kri = (EditText)findViewById(R.id.kriteriad);
        pol = (EditText)findViewById(R.id.polsekd);
        tgl = (EditText)findViewById(R.id.tanggald);
        jdl = (EditText)findViewById(R.id.juduld);
        ket = (EditText)findViewById(R.id.ketd);
        lokasi = (EditText)findViewById(R.id.lokasid);
        status = (EditText)findViewById(R.id.statusd);
        urlimg = (TextView)findViewById(R.id.urlimgd);
        imgD = (ImageView)findViewById(R.id.imgdLaporan);
        spin_status = (Spinner) findViewById(R.id.spinstatus);

        //ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dialogs);
        //spin_status.setAdapter(aa);

        getData();
        Status();
    }

    private void Status(){
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dialogs);
        spin_status.setAdapter(aa);
        spin_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                Toast.makeText(DetailPengaduan.this, "Status Dirubah : " + dialogs[i], Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(null, "Silahkan memilih salah satu", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getData(){
        class getDetail extends AsyncTask<Void, Void,String>{

            ProgressDialog loding;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loding = ProgressDialog.show(DetailPengaduan.this, "Mencocokkan data...","Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loding.dismiss();
                LihatData(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(SettingDB.URL_GET_DETAIL, idnama);
                return s;
            }
        }
        getDetail ge = new getDetail();
        ge.execute();
    }

    private void LihatData(String json){

        try {
            Log.d("asasf", "hjbjhb");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(SettingDB.TAG_JSON_ARRAY);

            Log.d("isi json : ", result.toString());

            JSONObject obj = result.getJSONObject(0);
            Log.d("obj", obj.toString());

            String h1 = obj.getString(SettingDB.TAG_KATEGORI);
            String h2 = obj.getString(SettingDB.TAG_TGL);
            String h3 = obj.getString(SettingDB.TAG_JUDUL);
            String h4 = obj.getString(SettingDB.TAG_KET);
            String h5 = obj.getString(SettingDB.TAG_LOKASI);
            String h6 = obj.getString(SettingDB.TAG_IMAGE);
            String h7 = obj.getString(SettingDB.TAG_STATUS);
            String h8 = obj.getString(SettingDB.TAG_ID);
            String h9 = obj.getString(SettingDB.TAG_KRITERIA);
            String h10 = obj.getString(SettingDB.TAG_POLSEK);


            kat.setText(h1);
            tgl.setText(h2);
            jdl.setText(h3);
            ket.setText(h4);
            lokasi.setText(h5);
            urlimg.setText(h6);
            status.setText(h7);
            ids.setText(h8);
            kri.setText(h9);
            pol.setText(h10);

            URL = String.valueOf(urlimg.getText());
            Glide.with(this).load(URL)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgD);

        } catch (JSONException e) {
            Log.d("sdfhdf","asdasdasd");
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
