package com.sheoran.dinesh.androidquiz.listener;

// Created by Dinesh Kumar on 5/2/2019
public interface FirebaseDataChangeNotifier {

    void onLoad(boolean isSuccess, String msg);

    void onDelete(boolean isSuccess, String msg);

    void onUpdate(boolean isSuccess, String msg);

    void onAdd(boolean isSuccess, String msg);
}
