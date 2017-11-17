package com.samsung.muhammad.polisi.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.samsung.muhammad.polisi.R;
import com.samsung.muhammad.polisi.activity.DepanActivity;
import com.samsung.muhammad.polisi.activity.DetailNews;
import com.samsung.muhammad.polisi.activity.PantauDanaActivity;
import com.samsung.muhammad.polisi.activity.PengaduanActivity;
import com.samsung.muhammad.polisi.activity.ProfilActivity;
import com.samsung.muhammad.polisi.activity.ProfilePolresActivity;
import com.samsung.muhammad.polisi.activity.RembukDesaActivity;
import com.samsung.muhammad.polisi.adapter.HomeMenuAdapter;
import com.samsung.muhammad.polisi.adapter.ListNewsAdapter;
import com.samsung.muhammad.polisi.app.AppController;
import com.samsung.muhammad.polisi.data.NewsData;
import com.samsung.muhammad.polisi.listener.HomeMenuListener;
import com.samsung.muhammad.polisi.listener.NewsClickListener;
import com.samsung.muhammad.polisi.other.DividerItemDecoration;
import com.samsung.muhammad.polisi.other.EndlessRecyclerViewScrollListener;
import com.samsung.muhammad.polisi.other.RecyclerContextMenuInfoWrapperView;
import com.samsung.muhammad.polisi.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dody on 11/15/17.
 */

public class HomeFragment extends Fragment implements HomeMenuListener, NewsClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipe;
    List<NewsData> newsList = new ArrayList<NewsData>();

    private static final String TAG = HomeFragment.class.getSimpleName();

    private static String url_list = Server.URL + "news.php?offset=";

    private int offSet = 0;

    int no;

    @BindView(R.id.rv_menu)
    RecyclerView rvMenu;
    private HomeMenuAdapter menuAdapter;

    @BindView(R.id.rv_news)
    RecyclerView rvNews;
    ListNewsAdapter adapter;

    private EndlessRecyclerViewScrollListener scrollListener;
    private boolean isLoadAllFinished = false;
    private boolean isrefreshing = false;

    public static final String TAG_NO = "no";
    public static final String TAG_ID = "id";
    public static final String TAG_JUDUL = "judul";
    public static final String TAG_TGL = "tgl";
    public static final String TAG_ISI = "isi";
    public static final String TAG_GAMBAR = "gambar";

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        menuAdapter = new HomeMenuAdapter();
        menuAdapter.setListener(this);

        LinearLayoutManager menuLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rvMenu.setLayoutManager(menuLayoutManager);
        rvMenu.setNestedScrollingEnabled(false);
        rvMenu.setAdapter(menuAdapter);

        adapter = new ListNewsAdapter(newsList, getActivity());
        adapter.setListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rvNews.setLayoutManager(layoutManager);
        rvNews.setNestedScrollingEnabled(false);
        rvNews.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.SMALL_SIZE_TYPE));
        rvNews.setAdapter(adapter);

        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
               @Override
               public void run() {
                   isLoadAllFinished = false;
                   swipe.setRefreshing(true);
                   newsList.clear();
                   adapter.notifyDataSetChanged();
                   callNews(0);
               }
           }
        );

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(!isLoadAllFinished && !isrefreshing){
                    callNews(offSet);
                }
            }
        };

        rvNews.addOnScrollListener(scrollListener);

    }

    @Override
    public void onMenuClicked(int position) {
        if (position == 0) {
            Intent intent = new Intent(context, ProfilePolresActivity.class);
            startActivity(intent);
        } else if (position == 1) {
            Intent intent = new Intent(context, PantauDanaActivity.class);
            startActivity(intent);
        } else if (position == 2) {
            Intent intent = new Intent(context, RembukDesaActivity.class);
            startActivity(intent);
        } else if (position == 3) {
            Intent intent = new Intent(context, PengaduanActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = ((RecyclerContextMenuInfoWrapperView.RecyclerContextMenuInfo) item.getMenuInfo()).position;

        switch (item.getItemId()) {
            case R.id.menu_share:{
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, newsList.get(position).getJudul());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share news to.."));
                return true;
            }

            case R.id.menu_comment:{
                Intent intent = new Intent(getActivity(), DetailNews.class);
                intent.putExtra(TAG_ID, newsList.get(position).getId());
                startActivity(intent);
                return true;
            }

            default:
                return super.onContextItemSelected(item);
        }
    }

    private void callNews(int page) {

        swipe.setRefreshing(true);

        // Creating volley request obj
        JsonArrayRequest arrReq = new JsonArrayRequest(url_list + page,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());

                        swipe.setRefreshing(false);
                        isrefreshing = false;

                        if (response.length() > 0) {

                            isLoadAllFinished = false;

                            List<NewsData> list = new ArrayList<>();

                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    NewsData news = new NewsData();

                                    no = obj.getInt(TAG_NO);

                                    news.setId(obj.getString(TAG_ID));
                                    news.setJudul(obj.getString(TAG_JUDUL));

                                    if (obj.getString(TAG_GAMBAR) != "") {
                                        news.setGambar(obj.getString(TAG_GAMBAR));
                                    }

                                    news.setDatetime(obj.getString(TAG_TGL));
                                    news.setIsi(obj.getString(TAG_ISI));

                                    // adding news to news array
                                    list.add(news);

                                    if (no > offSet)
                                        offSet = no;

                                    Log.e(TAG, "offSet " + offSet);

                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }

                                // notifying list adapter about data changes
                                // so that it renders the list view with updated data
                                adapter.notifyDataSetChanged();
                            }

                            newsList.addAll(list);
                            adapter.notifyDataSetChanged();
                        } else {
                            isLoadAllFinished = true;
                            adapter.notifyDataSetChanged();
                        }

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
                isrefreshing = false;
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(arrReq);
    }

    @Override
    public void onNewsClicked(String id) {
        Intent intent = new Intent(getActivity(), DetailNews.class);
        intent.putExtra(TAG_ID, id);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        if(isrefreshing){
            swipe.setRefreshing(false);
        } else{
            isrefreshing = true;
            newsList.clear();
            adapter.notifyDataSetChanged();
            offSet = 0;
            callNews(offSet);
        }

    }
}
