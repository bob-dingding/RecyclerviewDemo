package com.hxzk.bj.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    Button btn_LinearLayout,btn_GridLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {

        btn_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LinearLayoutActivity.class));
            }
        });
                btn_GridLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this,GridLayoutManagerActivity.class));
                    }
                });

    }

    private void initData() {

    }

    private void initView() {
        btn_LinearLayout=findViewById(R.id.btn_linearLayout);
        btn_GridLayout=findViewById(R.id.btn_gridlayout);


    }


}
