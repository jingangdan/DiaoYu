package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.project.dyuapp.R;

/**
 * @author gengqiufang
 * @describtion
 * @created at 2018/1/2 0002
 */
public class StartActivity extends AppCompatActivity {
    //在这里把isExit的值变为false
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mHandler.sendEmptyMessageDelayed(0, 2000);
    }
}
