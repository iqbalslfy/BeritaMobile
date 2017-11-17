package com.samsung.muhammad.polisi.other;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by dody on 11/16/17.
 */

public class RecyclerContextMenuInfoWrapperView extends FrameLayout {

    private RecyclerView.ViewHolder mHolder;
    private final View mView;

    public RecyclerContextMenuInfoWrapperView(View view) {
        super(view.getContext());
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mView = view;
        addView(mView);
    }

    public void setHolder(RecyclerView.ViewHolder holder) {
        mHolder = holder;
    }

    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return new RecyclerContextMenuInfo(mHolder.getPosition(), mHolder.getItemId());
    }

    public static class RecyclerContextMenuInfo implements ContextMenu.ContextMenuInfo {

        public RecyclerContextMenuInfo(int position, long id) {
            this.position = position;
            this.id = id;
        }

        final public int position;
        final public long id;
    }
}
