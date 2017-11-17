package com.samsung.muhammad.polisi.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.samsung.muhammad.polisi.data.NewsData;
import com.samsung.muhammad.polisi.listener.NewsClickListener;
import com.samsung.muhammad.polisi.other.RecyclerContextMenuInfoWrapperView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dody on 01/08/2017.
 */

public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsAdapter.ViewHolder>{

    private Context context;
    private Activity activity;
    private List<NewsData> itemList;

    private NewsClickListener listener;

    public ListNewsAdapter(List<NewsData> itemList, Activity activity){
        this.itemList = itemList;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        context = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final NewsData item = itemList.get(position);

        try {
            Glide.with(context)
                    .load(item.getGambar())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .crossFade()
                    .into(holder.ivImage);
        } catch (IllegalArgumentException e){}

        holder.tvTitle.setText(item.getJudul());

        String isi = stripHtml(item.getIsi());

        if (isi.length() >= 50)
            holder.tvDesc.setText(isi.substring(0, 50).trim());
        else
            holder.tvDesc.setText(isi.trim());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onNewsClicked(item.getId());
                }
            }
        });

        holder.ivOptions.setOnClickListener(new ShowContextMenuListener());
    }

    @Override
    public int getItemCount(){
        return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        @BindView(R.id.container)
        LinearLayout container;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.iv_profile_photo)
        ImageView ivProfilePhoto;
        @BindView(R.id.iv_options)
        ImageView ivOptions;

        public ViewHolder(View view){
            super(new RecyclerContextMenuInfoWrapperView(view));
            ((RecyclerContextMenuInfoWrapperView) itemView).setHolder(this);
//            super(itemView);
            ButterKnife.bind(this, itemView);

            activity.registerForContextMenu(itemView);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(Menu.NONE, R.id.menu_share, 0, context.getResources().getString(R.string.share_news));
            contextMenu.add(Menu.NONE, R.id.menu_comment, 0, context.getResources().getString(R.string.comment_news));
        }
    }

    public void setListener(NewsClickListener listener) {
        this.listener = listener;
    }

    public String stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(html).toString();
        }
    }

    private class ShowContextMenuListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            RecyclerView recyclerView = (RecyclerView) view.getParent().getParent().getParent().getParent().getParent().getParent();
            recyclerView.showContextMenuForChild(view);
        }
    }
}
