package com.sheoran.dinesh.androidquiz.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sheoran.dinesh.androidquiz.R;

//Dinesh Sheoran's
public class BaseActivity  extends AppCompatActivity {
    protected Toolbar toolbar;

    public void setToolbar(int drawable) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        if(ab!=null) {
            ab.setHomeAsUpIndicator(drawable);
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected String getResourceString(int id){
        return this.getResources().getString(id);
    }

    protected ProgressDialog getProgressDialog(int title, int msg){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getResourceString(title));
        progressDialog.setMessage(getResourceString(msg));
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        return progressDialog;
    }
}
