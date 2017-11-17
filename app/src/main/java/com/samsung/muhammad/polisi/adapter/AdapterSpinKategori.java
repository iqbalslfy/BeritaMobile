package com.samsung.muhammad.polisi.adapter;

/**
 * Created by ihsan on 10/10/2017.
 */
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.samsung.muhammad.polisi.R;
import com.samsung.muhammad.polisi.data.Data;

import java.util.List;



public class AdapterSpinKategori extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Data> item;

    public AdapterSpinKategori (Activity activity, List<Data> item){
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int i) {
        return item.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.activity_tambah_kasus, null);

        //TextView pendidikan = (TextView) view.findViewById(R.id.txtIsiKriteria);

        Data data;
        data = item.get(position);

        //pendidikan.setText(data.getKriteria());
        return view;

    }
}
