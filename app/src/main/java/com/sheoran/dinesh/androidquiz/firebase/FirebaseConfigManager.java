package com.sheoran.dinesh.androidquiz.firebase;

import android.content.Context;

// Created by Dinesh Kumar on 5/2/2019
public class FirebaseConfigManager {
    private CategoryFirebaseConfig _categoryFirebaseConfig;
    private QuestionFirebaseConfig _questionFirebaseConfig;
    private UserAccounFirebaseConfig _userAccounFirebaseConfig;

    private FirebaseConfigManager(Context context) {
        _categoryFirebaseConfig = CategoryFirebaseConfig.getInstance(context);
        _questionFirebaseConfig = QuestionFirebaseConfig.getInstance(context);
        _userAccounFirebaseConfig = UserAccounFirebaseConfig.getInstancce(context);
    }

    public static FirebaseConfigManager _instance;

    public static FirebaseConfigManager getInstance(Context context) {
        if (_instance == null) {
            _instance = new FirebaseConfigManager(context);
        }
        return _instance;
    }

    public CategoryFirebaseConfig getCategoryConfig() {
        return _categoryFirebaseConfig;
    }

    public QuestionFirebaseConfig getQuestionConfig() {
        return _questionFirebaseConfig;
    }

    public UserAccounFirebaseConfig getUserAccountFirebaseConfig(){
        return _userAccounFirebaseConfig;
    }
}
