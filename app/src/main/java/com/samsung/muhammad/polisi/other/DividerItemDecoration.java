package com.samsung.muhammad.polisi.other;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.samsung.muhammad.polisi.R;

/**
 * Created by Dody Arief on 6/8/2016.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    public static final int SMALL_SIZE_TYPE = 1;
    public static final int MEDIUM_SIZE_TYPE = 2;
    public static final int LARGE_SIZE_TYPE = 3;

    private Drawable mDivider;

    private int type;

    public DividerItemDecoration(Context context) {
        mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider);
        this.type = SMALL_SIZE_TYPE;
    }

    public DividerItemDecoration(Context context, int type) {
        if (type == SMALL_SIZE_TYPE)
            mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider);
        else if (type == MEDIUM_SIZE_TYPE)
            mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider_medium);
        else
            mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider_large);

        this.type = type;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int spacingInPixels = 0;

            if (type == SMALL_SIZE_TYPE) {
                spacingInPixels = child.getContext().getResources().getDimensionPixelSize(R.dimen.divider);
            } else if (type == MEDIUM_SIZE_TYPE) {
                spacingInPixels = child.getContext().getResources().getDimensionPixelSize(R.dimen.divider_medium);
            } else {
                spacingInPixels = child.getContext().getResources().getDimensionPixelSize(R.dimen.divider_large);
            }

            int top = child.getBottom() + params.bottomMargin - spacingInPixels;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
