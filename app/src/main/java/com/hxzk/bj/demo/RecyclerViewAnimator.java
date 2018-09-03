package com.hxzk.bj.demo;

import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：created by ${zjt} on 2018/9/3
 * 描述:
 */
public  class RecyclerViewAnimator extends SimpleItemAnimator {

      /**定义了remove的效果有一个滑动平移的效果**/
    List<RecyclerView.ViewHolder> removeHolders = new ArrayList<>();
    /**removeAnimators来管理所有的remove动画**/
    List<RecyclerView.ViewHolder> removeAnimators = new ArrayList<>();
    /**有飞出的动作还有一个上移的动作，所以还需要定义一下move的效果**/
    List<RecyclerView.ViewHolder> moveHolders = new ArrayList<>();
    List<RecyclerView.ViewHolder> moveAnimators = new ArrayList<>();


    //移除数据时调用,在animateRemove将holder添加进list中
    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        removeHolders.add(holder);
        return true;
    }
    //添加元素时调用，通常返回true
    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        return false;
    }
//列表项位置移动时调用
//在remove的一瞬间,下方的item实际上就已经上移了，所以在animateMove中设置item的translationY使其保持在未上移的位置。
    @Override
    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        holder.itemView.setTranslationY(fromY - toY);
        moveHolders.add(holder);
        return true;
    }
    //列表项数据发生改变时调用
    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromLeft, int fromTop, int toLeft, int toTop) {
        return false;
    }
    //当有动画需要执行时调用,做动效处理
    @Override
    public void runPendingAnimations() {
        if(!removeHolders.isEmpty()) {
            for(RecyclerView.ViewHolder holder : removeHolders) {
                remove(holder);
            }
            removeHolders.clear();
        }
        if(!moveHolders.isEmpty()){
            for(RecyclerView.ViewHolder holder : moveHolders) {
                move(holder);
            }
            moveHolders.clear();
        }
    }
    //当某个动画需要被立即停止时调用，这里一般做视图的状态恢复。
    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {
    }
    //停止多个动画时调用和endAnimation作用一样
    @Override
    public void endAnimations() {
    }
    //返回当前是否有动画正在运行
    @Override
    public boolean isRunning() {
        return !(removeHolders.isEmpty() && removeAnimators.isEmpty()&& moveHolders.isEmpty() && moveAnimators.isEmpty());
    }


    /**
     * 自定义执行动画,来执行来一个移动动画
     * @param holder
     */
    private void remove(final RecyclerView.ViewHolder holder){
        removeAnimators.add(holder);
        TranslateAnimation animation = new TranslateAnimation(0, 1000, 0, 0);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                dispatchRemoveStarting(holder);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                removeAnimators.remove(holder);
                dispatchRemoveFinished(holder);
                if(!isRunning()){//判断所有的remove动画是否结束
                    dispatchAnimationsFinished();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        holder.itemView.startAnimation(animation);
    }

    private void move(final RecyclerView.ViewHolder holder){
      moveAnimators.add(holder);
        ObjectAnimator animator = ObjectAnimator.ofFloat(holder.itemView,
                "translationY", holder.itemView.getTranslationY(), 0);
        animator.setDuration(500);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(android.animation.Animator animation) {
                dispatchMoveStarting(holder);
            }

            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                dispatchMoveFinished(holder);
                moveAnimators.remove(holder);
                if(!isRunning()) dispatchAnimationsFinished();
            }
        });
        animator.start();
    }


}
