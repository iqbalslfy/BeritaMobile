package com.samsung.muhammad.polisi.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.samsung.muhammad.polisi.R;
import com.samsung.muhammad.polisi.apihelper.http;
import com.samsung.muhammad.polisi.config.RequestHandler;
import com.samsung.muhammad.polisi.config.SettingDB;
import com.samsung.muhammad.polisi.data.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RankingActivity extends AppCompatActivity {

    private ListView listViews;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        listViews = (ListView) findViewById(R.id.listRanking);
        getJSON();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Laporan Kinerja");

        listViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), ListKasusActivity.class);
                HashMap<String, String> map = (HashMap) adapterView.getItemAtPosition(i);
                String kdPolsek = map.get("kdPolsek").toString();
                intent.putExtra("kdPolsek", kdPolsek);
                startActivity(intent);

            }
        });
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RankingActivity.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showRanking();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(http.URL_API + "/rank.php");
                return s;
            }
        }

        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void showRanking() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(SettingDB.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String rank = jo.getString("rank");
                String kdPolsek = jo.getString("polsek");
                String polsek = jo.getString("kantor");
                String poin = jo.getString("poin");


                HashMap<String, String> polseks = new HashMap<>();
                polseks.put("txtRank", rank);
                polseks.put("kdPolsek", kdPolsek);
                polseks.put("nama_polres", polsek);
                polseks.put("poin", poin);
                list.add(polseks);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(

                this, list, R.layout.list_rank_item,
                new String[]{"txtRank","kdPolsek","nama_polres","poin"},
                new int[]{R.id.txtRank,R.id.kode_id, R.id.nama_polres,R.id.poin });

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
