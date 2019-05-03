package com.sheoran.dinesh.androidquiz.firebase;

import android.content.Context;

// Created by Dinesh Kumar on 5/2/2019
public class FirebaseConfigManager {
    public static FirebaseConfigManager _instance;

    private CategoryFirebaseConfig _categoryFirebaseConfig;
    private QuestionFirebaseConfig _questionFirebaseConfig;
    private UserAccounFirebaseConfig _userAccounFirebaseConfig;

    private FirebaseConfigManager(Context context) {
        _userAccounFirebaseConfig = UserAccounFirebaseConfig.getInstancce(context);
        _categoryFirebaseConfig = CategoryFirebaseConfig.getInstance(context);
        _questionFirebaseConfig = QuestionFirebaseConfig.getInstance(context);
    }



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

    public UserAccounFirebaseConfig getUserAccountConfig(){
        return _userAccounFirebaseConfig;
    }
}
