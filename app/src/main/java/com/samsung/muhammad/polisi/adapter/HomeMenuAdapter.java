package com.samsung.muhammad.polisi.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.samsung.muhammad.polisi.R;
import com.samsung.muhammad.polisi.listener.HomeMenuListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dody on 01/08/2017.
 */

public class HomeMenuAdapter extends RecyclerView.Adapter<HomeMenuAdapter.ViewHolder>{

    private Context context;
    private HomeMenuListener listener;

    public HomeMenuAdapter(){

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        switch(position){
            case 0:
                holder.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_polres));
                break;
            case 1:
                holder.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_pantau_dana));
                break;
            case 2:
                holder.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_rembuk_desa));
                break;
            case 3:
                holder.image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_pengaduan));
                break;
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onMenuClicked(position);
                }
            }
        });

    }

    @Override
    public int getItemCount(){
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_image)
        ImageView image;
        @BindView(R.id.container)
        LinearLayout container;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setListener(HomeMenuListener listener) {
        this.listener = listener;
    }
}
