package com.samsung.muhammad.polisi.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.samsung.muhammad.polisi.R;
import com.samsung.muhammad.polisi.adapter.AdapterSpinKriteria;
import com.samsung.muhammad.polisi.adapter.AdapterSpinKategori;
import com.samsung.muhammad.polisi.apihelper.AppController;
import com.samsung.muhammad.polisi.config.RequestHandler;
import com.samsung.muhammad.polisi.config.SettingDB;
import com.samsung.muhammad.polisi.data.Data;

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

import static com.samsung.muhammad.polisi.config.SettingDB.TAG_ID;
import static com.samsung.muhammad.polisi.config.SettingDB.TAG_KATEGORI;
import static com.samsung.muhammad.polisi.config.SettingDB.TAG_KRITERIA;
import static com.samsung.muhammad.polisi.config.SettingDB.URL_GET_KRITERIA;

/**
 * Created by muhammad on 17/09/17.
 */

public class TambahKasus extends AppCompatActivity implements View.OnClickListener {

    private TextView kd_kriteria, kriteria,tvspinkriteria;
    private EditText  tgl, judul,ket,lokasi,txtkriteria,txtpolsek;
    private ImageView img;
    private Spinner spin_status,spin_kriteria,spin_kategori,kategori;
    private Button btnGal, btnSubmit;
    private String id;
    public static final String TAG_I = "id";
    public static final String TAG_K = "nama_kriteria";

    String [] dialogs = {"Proses", "Selesai"};
    String [] dialogs2= {"Baru","Proses","Selesai"};
    String [] listkategori = {"1. Penanganan Kasus","2. Non Penanganan Kasus"};
    String [] listkriteria = {"1. Peningkatan Kinerja","2. Pembenahan Kultur","3. Pengelolaan Media"};
    private static final int PICT_IMAGE = 100;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60;

    Bitmap bitmap, decode;
    LoginResponse user;

    List<Data> listKriteria = new ArrayList<Data>();
    AdapterSpinKriteria adapterSpinKriteria;

    List<Data> listKategori = new ArrayList<Data>();
    AdapterSpinKategori adapterSpinKategori;

    ProgressDialog pDialog;
    public static final String TAG = TambahKasus.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kasus);
        Hawk.init(this).build();
        this.user = Hawk.get("user");
        //Toast.makeText(getApplicationContext(), "user adalah"+user.getKd_polsek(), Toast.LENGTH_SHORT).show();


        Intent intent = getIntent();
        id = intent.getStringExtra(SettingDB.KEY_KODE);

        //txtkriteria = (EditText) findViewById(R.id.txtIsiKriteria);

        kd_kriteria = (TextView) findViewById(R.id.tvkd_kriteria);
        kriteria = (TextView) findViewById(R.id.tvkriteria);
        tvspinkriteria = (TextView) findViewById(R.id.tvspinkriteria);
        tgl = (EditText)findViewById(R.id.tgl);
        judul = (EditText) findViewById(R.id.judul);
       // kategori = (Spinner) findViewById(R.id.spinkategori);
        ket = (EditText) findViewById(R.id.keterangan);
        lokasi = (EditText) findViewById(R.id.lokasi);

        img = (ImageView)findViewById(R.id.imgLaporan);

        spin_status = (Spinner) findViewById(R.id.status);
        spin_kategori = (Spinner)findViewById(R.id.spinkategori);
        spin_kriteria = (Spinner)findViewById(R.id.spinkriteria);

        btnGal = (Button)findViewById(R.id.btnGallery);
        btnSubmit = (Button)findViewById(R.id.btnSimpan);

        btnGal.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

       adapterSpinKriteria = new AdapterSpinKriteria(TambahKasus.this, listKriteria);
        spin_kriteria.setAdapter(adapterSpinKriteria);

        adapterSpinKategori = new AdapterSpinKategori(TambahKasus.this, listKategori);
        spin_kriteria.setAdapter(adapterSpinKategori);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Tambah Kegiatan");
        //callDataKriteria();

        AmbilData();
        Status();
        kategori();
        kriteria();

       // spin_kategori.getItemAtPosition(spin_kategori.getSelectedItemPosition()).toString();


    }


    private void callDataKriteria() {
        listKriteria.clear();
        pDialog = new ProgressDialog(TambahKasus.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading Kriteria..");
        showDialog();

        JsonArrayRequest jArr = new JsonArrayRequest(URL_GET_KRITERIA,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);

                                Data item = new Data();

                                item.setId(obj.getString(TAG_I));
                                item.setKriteria(obj.getString(TAG_K));

                                listKriteria.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapterSpinKriteria.notifyDataSetChanged();

                        hideDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(TambahKasus.this, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
        //AppController.getInstance().addToRequestQueue(jArr);
      Log.d("sdf", jArr.toString());
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void Status(){
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dialogs);
        spin_status.setAdapter(aa);
        spin_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                //Toast.makeText(TambahKasus.this, "Status Terpilih : " + dialogs[i], Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(null, "Silahkan memilih salah satu", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void kategori(){
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listkategori);
        spin_kategori.setAdapter(aa);
        spin_kategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                //Toast.makeText(TambahKasus.this, "Kategori Terpilih : " + i, Toast.LENGTH_LONG).show();
                if(listkategori[i]=="1. Penanganan Kasus") {
                    spin_kriteria.setVisibility(View.INVISIBLE);
                    tvspinkriteria.setVisibility(View.INVISIBLE);
                    ubah_status(i);

                }
                else {
                    spin_kriteria.setVisibility(View.VISIBLE);
                    tvspinkriteria.setVisibility(View.VISIBLE);
                    ubah_status(i);

                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(null, "Silahkan memilih salah satu", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void ubah_status(int data)
    {
        if(data==0 ) {
            ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dialogs);
            spin_status.setAdapter(aa);
        }
        else
        {
            ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dialogs2);
            spin_status.setAdapter(aa);
        }
    }
    private void kriteria(){
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listkriteria);
        spin_kriteria.setAdapter(aa);
        spin_kriteria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                //Toast.makeText(TambahKasus.this, "Status Terpilih : " + dialogs[i], Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(null, "Silahkan memilih salah satu", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void clear(){
        kd_kriteria.setText("");
        tgl.setText("");
        judul.setText("");
        //kategori.setText("");
        ket.setText("");
        lokasi.setText("");
        img.setImageResource(0);
        spin_status.setSelected(true);
    }

    private void InsertToDatabase(){
//        final String kd_kr = kd_kriteria.getText().toString().trim();
        final String jdl = judul.getText().toString().trim();
        final String tanggal = tgl.getText().toString().trim();
        final String kat = spin_kategori.getSelectedItem().toString().trim();
        final String kri = spin_kriteria.getSelectedItem().toString().trim();
        final String lok = lokasi.getText().toString().trim();
        final String keterangan = ket.getText().toString().trim();
        final String stat = spin_status.getSelectedItem().toString().trim();
        final String kd_polsek=user.getKd_polsek();




        class InsertToDatabase extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TambahKasus.this, s , Toast.LENGTH_SHORT).show();
                clear();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(TambahKasus.this, "Menambahkan", "Tunggu",false,false);

            }

            @Override
            protected String doInBackground(Void... voids) {
                 String nilai;
                nilai="0";

                if(stat=="Baru")
                {
                    nilai = "3";
                }
                else if(stat=="Proses")
                {
                    nilai = "6";
                }
                else if(stat=="Selesai")
                {
                    nilai = "10";
                }

                HashMap<String,String> params = new HashMap<>();
                params.put(SettingDB.KEY_JUDUL, jdl);
                params.put(SettingDB.KEY_TGL, tanggal);
                params.put(SettingDB.KEY_KRITERIA, kri);
                params.put(SettingDB.KEY_KATEGORI, kat);

                params.put(SettingDB.KEY_LOKASI, lok);
                params.put(SettingDB.KEY_KET, keterangan);
                params.put(SettingDB.KEY_IMAGE, getStringImage(decode));
                params.put(SettingDB.KEY_STATUS, stat);
                params.put(SettingDB.KEY_KDPOLSEK,kd_polsek);
                params.put(SettingDB.KEY_NILAI,nilai);


                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(SettingDB.URL_ADD, params);

                return res;
            }
        }

        InsertToDatabase toDatabase = new InsertToDatabase();
        toDatabase.execute();
    }

    private void AmbilData(){
        class getdata extends AsyncTask<Void,Void,String>{

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Tampil(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(SettingDB.URL_GET_DETAIL, id);
                return s;
            }
        }

        getdata data = new getdata();
        data.execute();

    }

    private void Tampil(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(SettingDB.TAG_JSON_ARRAY);
            JSONObject obj = result.getJSONObject(0);
            String h1 = obj.getString(SettingDB.TAG_KASUS);
            String h2 = obj.getString(SettingDB.TAG_KODE);

            kd_kriteria.setText(h2);
            kriteria.setText(h1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void takeImageFromGalery(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decode = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        img.setImageBitmap(decode);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //mengambil fambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                setToImageView(getResizedBitmap(bitmap, 1024));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnGal){
            takeImageFromGalery(view);
        }else if (view == btnSubmit){
        InsertToDatabase();
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
