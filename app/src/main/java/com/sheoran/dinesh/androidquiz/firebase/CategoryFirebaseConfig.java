package com.sheoran.dinesh.androidquiz.firebase;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sheoran.dinesh.androidquiz.listener.FirebaseDataChangeNotifier;
import com.sheoran.dinesh.androidquiz.model.Category;
import com.sheoran.dinesh.androidquiz.util.Constants;
import com.sheoran.dinesh.androidquiz.util.FirebaseHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Created by Dinesh Kumar on 5/2/2019
public class CategoryFirebaseConfig extends FirebaseHelper {
    private static CategoryFirebaseConfig _instance;

    private List<Category> _categoryList = new ArrayList<>();

    private CategoryFirebaseConfig(Context context) {
        super(context, Constants.FIREBASE_CATEGORY_REF);
    }

    public static CategoryFirebaseConfig getInstance(Context context) {
        if (_instance == null) {
            _instance = new CategoryFirebaseConfig(context);
        }
        return _instance;
    }

    public void requestToLoad() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                _categoryList.clear();

                Category category;

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {

                    DataSnapshot dataShot = iterator.next();
                    category = dataShot.getValue(Category.class);

                    _categoryList.add(category);
                }

                firebaseDataChangeNotifier.onAdd(true, "load categories successfully !");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                firebaseDataChangeNotifier.onAdd(false, "Unable to load categories");
            }
        });
    }

    public List<Category> getCategoriesList() {
        return _categoryList;
    }

    @Override
    public void setFirebaseDataChangeNotifier(FirebaseDataChangeNotifier listener) {
        firebaseDataChangeNotifier = listener;
    }

    @Override
    public void unsetFirebaseDataChangeNotifier() {
        firebaseDataChangeNotifier = null;
    }
}
