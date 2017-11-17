package com.samsung.muhammad.polisi.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.samsung.muhammad.polisi.R;
import com.samsung.muhammad.polisi.adapter.ListCommentsAdapter;
import com.samsung.muhammad.polisi.app.AppController;
import com.samsung.muhammad.polisi.app.ServerManager;
import com.samsung.muhammad.polisi.data.Comment;
import com.samsung.muhammad.polisi.other.DividerItemDecoration;
import com.samsung.muhammad.polisi.util.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by Kuncoro on 29/02/2016.
 */
public class DetailNews extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final int SUBMIT_COMMENT_REQ_CODE = 1000;

    @BindView(R.id.gambar_news)
    ImageView thumb_image;
    @BindView(R.id.judul_news)
    TextView judul;
    @BindView(R.id.tgl_news)
    TextView tgl;
    @BindView(R.id.isi_news)
    WebView isi;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipe;

    String id_news;

    private static final String TAG = DetailNews.class.getSimpleName();

    public static final String TAG_ID = "id";
    public static final String TAG_JUDUL = "judul";
    public static final String TAG_TGL = "tgl";
    public static final String TAG_ISI = "isi";
    public static final String TAG_GAMBAR = "gambar";

    private static final String url_detail = Server.URL + "detail_news.php";
    String tag_json_obj = "json_obj_req";

    private List<Comment> comments = new ArrayList<>();
    private ListCommentsAdapter adapter;
    @BindView(R.id.rv_comments)
    RecyclerView rvComments;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Detail News");

        id_news = getIntent().getStringExtra(TAG_ID);

        swipe.setOnRefreshListener(this);

        WebSettings webSettings = isi.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultFontSize(16);

        swipe.post(new Runnable() {
               @Override
               public void run() {
                   if (!id_news.isEmpty()) {
                       callDetailNews(id_news);
                   }
               }
           }
        );

        adapter = new ListCommentsAdapter(comments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvComments.setLayoutManager(layoutManager);
        rvComments.setNestedScrollingEnabled(false);
        rvComments.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.SMALL_SIZE_TYPE));
        rvComments.setAdapter(adapter);

        loadComments();

    }

    private void callDetailNews(final String id) {
        swipe.setRefreshing(true);

        StringRequest strReq = new StringRequest(Request.Method.POST, url_detail, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "ResponseData " + response.toString());
                swipe.setRefreshing(false);

                try {
                    JSONObject obj = new JSONObject(response);

                    String Judul = obj.getString(TAG_JUDUL);
                    String Gambar = obj.getString(TAG_GAMBAR);
                    String Tgl = obj.getString(TAG_TGL);
                    String Isi = obj.getString(TAG_ISI);

                    judul.setText(Judul);
                    tgl.setText(Tgl);

                    String header = "<html><head><style type=\"text/css\">"
                            + "body {line-height:20px;padding-left:6px;padding-right:6px;text-align:left;color:#9b9b9b;height:auto;} "
                            + "img {max-width:100%;height:auto;}"
                            + "iframe {max-width:100%;}"
                            + "</style></head><body>";
                    String footer = "</body></html>";

                    isi.loadDataWithBaseURL(null, header + Isi + footer,
                            "text/html", "UTF-8", null);

                    if (obj.getString(TAG_GAMBAR) != "") {
                        Glide.with(DetailNews.this)
                                .load(Gambar)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(thumb_image);
//                        thumb_image.setImageUrl(Gambar, imageLoader);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Detail News Error: " + error.getMessage());
                Toast.makeText(DetailNews.this,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                swipe.setRefreshing(false);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        callDetailNews(id_news);
    }

    private void loadComments() {

        Call<List<Comment>> call = ServerManager.getInstance().getService().getNewsComments(id_news);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, retrofit2.Response<List<Comment>> response) {
                if(response.body() == null){
                    Toast.makeText(DetailNews.this, "Terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_LONG).show();
                    return;
                }

                List<Comment> items = response.body();

                if (items.size() > 0) {
                    comments.clear();
                    comments.addAll(items);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(DetailNews.this, "Terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick(R.id.bt_give_comment)
    public void giveComment() {
        Intent intent = new Intent(this, SubmitCommentActivity.class);
        intent.putExtra(SubmitCommentActivity.NEWS_ID, id_news);
        startActivityForResult(intent, SUBMIT_COMMENT_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SUBMIT_COMMENT_REQ_CODE) {
            switch (resultCode) {
                case RESULT_OK:{
                    loadComments();
                    break;
                }
            }
        }
    }
}