package sinwao.com.recycleviewdemo.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * 描述：
 * Created by artcool on 2017/4/13.
 */

public class MyDividerGridItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    private final Drawable mDivider;

    public MyDividerGridItemDecoration(Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(ATTRS);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        drawHorizontal(c,parent);
        drawVertical(c,parent);
    }

    /**
     * 绘制垂直方向的分割线
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            int top = view.getTop() - layoutParams.topMargin;
            int bottom = view.getBottom() + layoutParams.bottomMargin;
            int left = view.getRight() + layoutParams.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }

    }

    /**
     * 绘制水平方向的分割线
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            int left = view.getLeft() - layoutParams.leftMargin;
            int right = view.getRight() + layoutParams.rightMargin + mDivider.getIntrinsicWidth();
            int top = view.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        System.out.println("------->"+spanCount);
        int itemCount = parent.getAdapter().getItemCount();
        if (isLastRaw(parent,itemPosition,spanCount,itemCount)) {
            outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
        } else if (isLastColum(parent,itemPosition,spanCount,itemCount)) {
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0,0,mDivider.getIntrinsicWidth(),mDivider.getIntrinsicHeight());
        }
    }

    /**
     * 如果是最后一列则不需要绘制右边
     * @param parent
     * @param itemPosition
     * @param spanCount
     * @param itemCount
     * @return
     */
    private boolean isLastColum(RecyclerView parent, int itemPosition, int spanCount, int itemCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((itemPosition + 1) % spanCount == 0) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((itemPosition + 1) % spanCount == 0) {
                    return true;
                }
            } else {
                itemCount = itemCount - itemCount % spanCount;
                if (itemPosition >= itemCount) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 如果是最后一行则不需要绘制底部
     * @param parent
     * @param itemPosition
     * @param spanCount
     * @param itemCount
     * @return
     */
    private boolean isLastRaw(RecyclerView parent, int itemPosition, int spanCount, int itemCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            itemCount = itemCount - itemCount % itemPosition;
            if (itemPosition >= itemCount) {
                return  true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            /**
             * StaggeredGridLayoutManager且纵向滚动
             */
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                itemCount = itemCount - itemCount % spanCount;
                /**
                 * 如果是最后一行则不需要绘制底部
                 */
                if (itemPosition >= itemCount) {
                    return  true;
                }
            } else {
                /**
                 * StaggeredGridLayoutManager且横向滚动
                 */
                if ((itemPosition + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取列数
     * @param parent
     */
    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }
}
