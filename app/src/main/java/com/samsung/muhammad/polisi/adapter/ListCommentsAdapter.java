package com.samsung.muhammad.polisi.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.samsung.muhammad.polisi.R;
import com.samsung.muhammad.polisi.data.Comment;
import com.samsung.muhammad.polisi.listener.NewsClickListener;
import com.samsung.muhammad.polisi.other.RecyclerContextMenuInfoWrapperView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dody on 01/08/2017.
 */

public class ListCommentsAdapter extends RecyclerView.Adapter<ListCommentsAdapter.ViewHolder>{

    private Context context;
    private List<Comment> itemList;

    public ListCommentsAdapter(List<Comment> itemList){
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        context = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Comment item = itemList.get(position);

        if (!item.getImageUrl().equals("") || !TextUtils.isEmpty(item.getImageUrl())) {
            try {
                Glide.with(context)
                        .load(item.getImageUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .crossFade()
                        .into(holder.ivImage);

                holder.ivImage.setVisibility(View.VISIBLE);
            } catch (IllegalArgumentException e) {
            }
        } else {
            holder.ivImage.setVisibility(View.GONE);
        }

        holder.tvName.setText(item.getName());
        holder.tvComment.setText(item.getComment());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        try{
            dateFormat.parse(item.getDate());
            Calendar date = dateFormat.getCalendar();

            holder.tvDate.setText(date.get(Calendar.DAY_OF_MONTH) +
                    "-" + (date.get(Calendar.MONTH) + 1) +
                    "-" + date.get(Calendar.YEAR));

        } catch(ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.container)
        LinearLayout container;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_comment)
        TextView tvComment;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.iv_profile_photo)
        ImageView ivProfilePhoto;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
