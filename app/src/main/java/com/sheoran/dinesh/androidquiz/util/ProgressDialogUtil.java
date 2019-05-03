package com.sheoran.dinesh.androidquiz.util;

import android.app.ProgressDialog;
import android.content.Context;

// Created by Dinesh Kumar on 5/2/2019
public class ProgressDialogUtil {
    public static ProgressDialog getDialog(Context context,String title,String msg){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(msg);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        return progressDialog;
    }
}
