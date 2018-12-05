package com.sheoran.dinesh.androidquiz;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.sheoran.dinesh.androidquiz.activity.BaseActivity;
import com.sheoran.dinesh.androidquiz.fragment.CategoriesFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadCategoriesFragment();
    }

    private void loadCategoriesFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new CategoriesFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
