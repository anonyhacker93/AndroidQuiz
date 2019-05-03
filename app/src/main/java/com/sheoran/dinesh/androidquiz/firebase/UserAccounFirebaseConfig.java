package com.sheoran.dinesh.androidquiz.firebase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sheoran.dinesh.androidquiz.R;
import com.sheoran.dinesh.androidquiz.activity.MainActivity;
import com.sheoran.dinesh.androidquiz.activity.UserAccountActivity;
import com.sheoran.dinesh.androidquiz.listener.FirebaseDataChangeNotifier;
import com.sheoran.dinesh.androidquiz.model.User;
import com.sheoran.dinesh.androidquiz.util.Constants;
import com.sheoran.dinesh.androidquiz.util.FirebaseHelper;

// Created by Dinesh Kumar on 5/2/2019
public class UserAccounFirebaseConfig extends FirebaseHelper {
    private static UserAccounFirebaseConfig _instance;

    private UserAccounFirebaseConfig(Context context) {
        super(context, Constants.FIREBASE_USER_REF);
    }

    public static UserAccounFirebaseConfig getInstancce(Context context) {
        if (_instance == null) {
            _instance = new UserAccounFirebaseConfig(context);
        }
        return _instance;
    }

    public void requestToCreateAccount(User user) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(user.getName()).exists()) {
                    firebaseDataChangeNotifier.onAdd(false, "User already exists !");
                } else {
                    databaseReference.child(user.getName()).setValue(user);
                    firebaseDataChangeNotifier.onAdd(true, "Account created successfully !");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                firebaseDataChangeNotifier.onAdd(false, "Error : Unable to create account !");
            }
        });
    }

    public void requestToLogin(User user) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user.getName()).exists()) {

                    if (!user.getPassword().isEmpty() && !user.getName().isEmpty()) {
                        User userInfo = dataSnapshot.child(user.getName()).getValue(User.class);
                        if (user.getPassword().equals(userInfo.getPassword())) {
                            firebaseDataChangeNotifier.onLoad(true, "Login successfully !");
                        } else {
                            firebaseDataChangeNotifier.onLoad(false, "Wrong Password/User Name!");
                        }
                    }
                } else {
                    firebaseDataChangeNotifier.onLoad(false, "User not existed");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                firebaseDataChangeNotifier.onLoad(false, "Error:Unable to login !");
            }
        });
    }

    @Override
    public void setFirebaseDataChangeNotifier(FirebaseDataChangeNotifier notifier) {
        firebaseDataChangeNotifier = notifier;
    }

    @Override
    public void unsetFirebaseDataChangeNotifier() {
        firebaseDataChangeNotifier = null;
    }
}
