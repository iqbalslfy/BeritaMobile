package com.samsung.muhammad.polisi.activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import com.samsung.muhammad.polisi.R;

public class DiskusiActivity extends AppCompatActivity {

    private WebView berita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_berita);
        getSupportActionBar().setTitle("Tentang Aplikasi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Komunikasi Personil");
        berita = (WebView)findViewById(R.id.webberita);
        WebSettings webSettings = berita.getSettings();

        berita.setWebViewClient(new WebViewClient());

        webSettings.setJavaScriptEnabled(true);
        berita.addJavascriptInterface(new WebAppInterface(this), "berita");
        berita.loadUrl("http://detikhost.com/promoter/sistem/forum/");

    }



    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
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
