package com.hxzk.bj.demo;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Collections;

/**
 * 作者：created by ${zjt} on 2018/8/31
 * 描述:
 */
public class RecyclerItemTouchHelperCallBack extends ItemTouchHelper.Callback {


    RecyclerViewAdapter mRecyclerViewAdapter;
    /**
     * 是否可以滑动，区分线性布局管理器和网格布局管理器
     **/
    boolean mIsSwipe;
    boolean mCanSwipeFirstOne;


    /**
     *
     * @param mRecyclerViewAdapter recyclerview的adapter
     * @param isSwipe 是否允许滑动
     * @param canSwipeFirstOne 是否第一个item固定
     */
    public RecyclerItemTouchHelperCallBack(RecyclerViewAdapter mRecyclerViewAdapter, boolean isSwipe, boolean canSwipeFirstOne) {
        this.mRecyclerViewAdapter = mRecyclerViewAdapter;
        this.mIsSwipe = isSwipe;
        this.mCanSwipeFirstOne = canSwipeFirstOne;
    }


    //根据返回值来决定是否处理拖动或滑动事件
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag;//拖拽类型
        int swipeFlag;//滑动类型
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            dragFlag = ItemTouchHelper.UP | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT | ItemTouchHelper.DOWN;
            swipeFlag = 0;//swipeFlag设置为0是暂时不处理滑动事件
        } else {
            dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

            swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;
        }
        return makeMovementFlags(dragFlag, swipeFlag);

    }
    //此处为滑动和拖拽的区别

    //长按进入拖拽状态不断回调此方法
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //根据拖拽的viewHolder获取其对应位置下标
        int currentPosition = viewHolder.getAdapterPosition();
        // //根据目标的viewHolder获取其对应位置下标
        int targetPosition = target.getAdapterPosition();
        if (mCanSwipeFirstOne && targetPosition == 0) {
            return false;
        }

        if (currentPosition < targetPosition) {
            for (int i = currentPosition; i < targetPosition; i++) {
                //向下一个item拖拽
                Collections.swap(mRecyclerViewAdapter.getAdapterList(), i, i + 1);

            }
        } else {
            for (int i = targetPosition; i > currentPosition; i--) {
                //向上一个item拖拽
                Collections.swap(mRecyclerViewAdapter.getAdapterList(), i, i - 1);

            }
        }
        //刷新拖拽区间的item
        mRecyclerViewAdapter.notifyItemMoved(currentPosition, targetPosition);
        return true;
    }

    //删除滑动的回调
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int currentIndex = viewHolder.getAdapterPosition();
        //刷新数据
        mRecyclerViewAdapter.notifyItemRemoved(currentIndex);
        //删除数据源集合中的对应数据
        mRecyclerViewAdapter.getAdapterList().remove(currentIndex);
    }


    //此方法选中拖拽item时调用
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        //如果不是闲置状态及被选中
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(Color.RED);
        }

    }

    //此方法松手拖拽item时调用
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        //松手恢复原背景
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
    }


    @Override
    public boolean isItemViewSwipeEnabled() {
        return mIsSwipe;
    }

    /**
     * 返回 false让它控制所有的 item 都不能拖曳,默认为true
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return mCanSwipeFirstOne;
    }
}
