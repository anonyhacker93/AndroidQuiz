package com.sheoran.dinesh.androidquiz;

import android.os.Bundle;

import com.sheoran.dinesh.androidquiz.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
