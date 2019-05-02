package com.sheoran.dinesh.androidquiz.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sheoran.dinesh.androidquiz.listener.FirebaseDataChangeNotifier;
import com.sheoran.dinesh.androidquiz.model.Questions;
import com.sheoran.dinesh.androidquiz.util.Constants;
import com.sheoran.dinesh.androidquiz.util.FirebaseHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Created by Dinesh Kumar on 5/2/2019
public class QuestionFirebaseConfig extends FirebaseHelper {
    private static QuestionFirebaseConfig _instance;

    private ArrayList<Questions> _questionList = new ArrayList<>();

    private FirebaseDataChangeNotifier _firebaseDataChangeNotifier;


    private QuestionFirebaseConfig(Context context) {
        super(context, Constants.FIREBASE_QUESTION_REFERENCE);
    }

    public static QuestionFirebaseConfig getInstance(Context context) {
        if (_instance == null) {
            _instance = new QuestionFirebaseConfig(context);
        }
        return _instance;
    }


    public void requestLoadQuestion(String categName) {
        fetchQuestionsByCategoryName(categName);
    }

    public List<Questions> getAllQuestionsList(){
        return _questionList;
    }

    private void fetchQuestionsByCategoryName(final String categName) {
        Log.d(Constants.LOG_TAG, "QuestionFirebaseHelper : fetchQuestionsByCategoryName starts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> iterator = dataSnapshot.child(categName).getChildren().iterator();
                Questions questions;
                _questionList.clear();
                while (iterator.hasNext()) {
                    DataSnapshot dataShot = iterator.next();
                    questions = dataShot.getValue(Questions.class);
                    _questionList.add(questions);
                }
                Log.d(Constants.LOG_TAG, "QuestionFirebaseConfig : fetchQuestionsByCategoryName onDataChange");

                if (_firebaseDataChangeNotifier != null) {
                    _firebaseDataChangeNotifier.onLoad(true, "Category fetched");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(Constants.LOG_TAG, "QuestionFirebaseConfig : Exception : " + databaseError.getMessage());
                if (_firebaseDataChangeNotifier != null) {
                    _firebaseDataChangeNotifier.onLoad(false, "Unable to fetch category");
                }
            }
        });
    }

    public void setFirebaseDataChangeNotifier(FirebaseDataChangeNotifier listener) {
        _firebaseDataChangeNotifier = listener;
    }

    @Override
    public void unsetFirebaseDataChangeNotifier() {
        _firebaseDataChangeNotifier = null;
    }
}
