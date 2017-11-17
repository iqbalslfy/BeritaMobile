package com.samsung.muhammad.polisi.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.samsung.muhammad.polisi.R;
import com.samsung.muhammad.polisi.config.RequestHandler;
import com.samsung.muhammad.polisi.config.SettingDB;
import com.samsung.muhammad.polisi.responses.LoginResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListLaporActivity extends AppCompatActivity  {

    private ListView listViews;
    private String JSON_STRING;
    private FloatingActionButton fab;
    private LoginResponse user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        listViews = (ListView) findViewById(R.id.listLaporan);
        Hawk.init(this).build();
        this.user = Hawk.get("user");
        //Toast.makeText(getApplicationContext(), "user adalah"+user.getKd_polsek(), Toast.LENGTH_SHORT).show();
        getJSON();

        fab = (FloatingActionButton)findViewById(R.id.fablapor);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListLaporActivity.this, TambahKasus.class);
                startActivity(intent);
            }
        });


        listViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), DetailPengaduan.class);
                HashMap<String,String> map =(HashMap)adapterView.getItemAtPosition(i);
                String empId = map.get(SettingDB.KEY_ID).toString();
                intent.putExtra(SettingDB.KEY_ID,empId);
                Log.d("isine : ", empId);
                startActivity(intent);

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Daftar Kegiatan");

    }


    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ListLaporActivity.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(SettingDB.URL_GET_ALL+"?polsek="+user.getKd_polsek());
                return s;
            }
        }

        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(SettingDB.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(SettingDB.TAG_ID);
                String judul = jo.getString(SettingDB.TAG_JUDUL);


                HashMap<String,String> employees = new HashMap<>();
                employees.put(SettingDB.TAG_ID, id);
                employees.put(SettingDB.TAG_JUDUL, judul);
                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(

                this, list, R.layout.list_item,
                new String[]{SettingDB.TAG_ID, SettingDB.TAG_JUDUL},
                new int[]{R.id.kode_id, R.id.nama_kasus});

        listViews.setAdapter(adapter);
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
