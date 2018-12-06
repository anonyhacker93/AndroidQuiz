package com.sheoran.dinesh.androidquiz.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.sheoran.dinesh.androidquiz.R;
import com.sheoran.dinesh.androidquiz.activity.BaseActivity;
import com.sheoran.dinesh.androidquiz.fragment.CategoriesFragment;
import com.sheoran.dinesh.androidquiz.fragment.HomeFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadHomeFragment();
    }

    private void loadHomeFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new HomeFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
