package com.samsung.muhammad.polisi.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.samsung.muhammad.polisi.product.Product;
import com.samsung.muhammad.polisi.R;

import java.util.List;

/**
 * Created by muhammad on 15/08/17.
 */

public class GridViewAdapter extends ArrayAdapter<Product> {
    public GridViewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (null == v){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.grid_item, null);
        }
        Product product = getItem(position);
        ImageView img =  v.findViewById(R.id.imageView);
        TextView txtTitle =  v.findViewById(R.id.txtTitle);
        TextView txtDescription = v.findViewById(R.id.textDescription);

        img.setImageResource(product.getImageId());
        txtTitle.setText(product.getTitle());
        txtDescription.setText(product.getDescription());

        return v;
    }
}
