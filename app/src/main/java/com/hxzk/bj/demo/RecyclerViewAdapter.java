package com.hxzk.bj.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * 作者：created by ${zjt} on 2018/8/29
 * 描述:
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {



    Context mContext;
    ArrayList<RecyclerViewBean> mArrayList;

    public RecyclerViewAdapter(Context mContext, ArrayList<RecyclerViewBean> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.adapter_item, parent, false);
//        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) mView.getLayoutParams();
//        layoutParams.topMargin=3;
//        layoutParams.bottomMargin=3;
//        layoutParams.leftMargin=3;
//        layoutParams.rightMargin=3;
//        mView.setLayoutParams(layoutParams);
        MyViewHolder myViewHolder =new MyViewHolder(mView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tvName.setText(mArrayList.get(position).name);
        holder.tvSex.setText(mArrayList.get(position).sex);
        holder.tvAge.setText(mArrayList.get(position).age + "");

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.LinearItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.LinearItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return mArrayList.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvSex;
        TextView tvAge;
        LinearLayout LinearItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_name);
            tvSex=itemView.findViewById(R.id.tv_sex);
            tvAge=itemView.findViewById(R.id.tv_age);
            LinearItem=itemView.findViewById(R.id.linear_item);
        }


    }


    private OnItemClickLitener mOnItemClickLitener;

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener OnItemClickLitener)
    {
        this.mOnItemClickLitener = OnItemClickLitener;
    }



    public void addItem(int position) {
        RecyclerViewBean mRecyclerViewBean =new RecyclerViewBean("增加用户"+position,100,"nan");
        mArrayList.add(position, mRecyclerViewBean);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mArrayList.remove(position);
        notifyItemRemoved(position);
    }

    public  ArrayList<RecyclerViewBean> getAdapterList(){
        return mArrayList;
    }

}
