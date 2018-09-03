package com.hxzk.bj.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * 作者：Created by Ding on 2018/9/2
 * 文件描述：线性布局管理器
 */
public class LinearLayoutActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    ArrayList<RecyclerViewBean> arrRecyclerBean;
    RecyclerViewAdapter mRecyclerViewAdapter;
    /**触摸或拖拽具体实现回调处理类 **/
    ItemTouchHelper itemTouchHelper;

    Button btn_LinearLayout,btn_GridLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        //第一种实现item监听的方式
//        mRecyclerViewAdapter.setOnItemClickLitener(new RecyclerViewAdapter.OnItemClickLitener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                mRecyclerViewAdapter.addItem(position);
//            }
//
//            @Override
//            public void onItemLongClick(View view, int position) {
//                mRecyclerViewAdapter.removeItem(position);
//            }
//        });
        //第二种实现item监听的方式
        mRecyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(mRecyclerView){

            @Override
            public void onShortItemListener(RecyclerView.ViewHolder viewHolder) {
                RecyclerViewAdapter.MyViewHolder myViewHolder= (RecyclerViewAdapter.MyViewHolder) viewHolder;
                String name= myViewHolder.tvName.getText().toString();
                Toast.makeText(LinearLayoutActivity.this,name,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongItemListener(RecyclerView.ViewHolder viewHolder) {
                RecyclerViewAdapter.MyViewHolder myViewHolder= (RecyclerViewAdapter.MyViewHolder) viewHolder;
                String name= myViewHolder.tvName.getText().toString();
                Toast.makeText(LinearLayoutActivity.this,name,Toast.LENGTH_SHORT).show();
                if (viewHolder.getLayoutPosition() != 0) {
                    itemTouchHelper.startDrag(viewHolder);
                }
            }
        });

    }

    private void initData() {
        arrRecyclerBean=new ArrayList<>();

        for(int i=0;i<20;i++){
            RecyclerViewBean mRecyclerBean;
            if(i%2 ==0){
                mRecyclerBean =new RecyclerViewBean("姓名"+i,10+i,"男");
            }else{
                mRecyclerBean =new RecyclerViewBean("姓名"+i,10+i,"女");
            }
            arrRecyclerBean.add(mRecyclerBean);
        }


        //设置为线性管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置item增加删除动画
       // mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置item自定义动画
        mRecyclerView.setItemAnimator(new RecyclerViewAnimator());
        //给item增加自带分割线
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        //给LinearLayout的item增加自定义分割线
       DividerItemDecoration mDividerItemDecoration =new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
       mDividerItemDecoration.setDrawable(ContextCompat.getDrawable(this,R.drawable.linearlayout_divider));
       mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mRecyclerViewAdapter=new RecyclerViewAdapter(this,arrRecyclerBean);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        itemTouchHelper
                = new ItemTouchHelper(new RecyclerItemTouchHelperCallBack(mRecyclerViewAdapter,true,true));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);


    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_main);
        btn_LinearLayout=findViewById(R.id.btn_linearLayout);
        btn_GridLayout=findViewById(R.id.btn_gridlayout);

        mRecyclerView.setVisibility(View.VISIBLE);
        btn_LinearLayout.setVisibility(View.GONE);
        btn_GridLayout.setVisibility(View.GONE);
    }


}
