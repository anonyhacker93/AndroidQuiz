package com.sheoran.dinesh.androidquiz.util;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sheoran.dinesh.androidquiz.listener.FirebaseDataChangeNotifier;

abstract public class FirebaseHelper {
    protected DatabaseReference databaseReference;
    protected FirebaseDataChangeNotifier firebaseDataChangeNotifier;

    public FirebaseHelper(Context context, String referenceName) {
        initFirebase(context, referenceName);
    }


    private void initFirebase(Context context, String referenceName) {
        FirebaseApp.initializeApp(context);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(referenceName);
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }


    public boolean deleteNode(Context context, String childName) {
        try {
            if (databaseReference.child(childName) != null) {
                databaseReference.child(childName).removeValue();
                return true;
            }
            {
                Toast.makeText(context, "This value does not exist in database", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception ex) {
            Toast.makeText(context, "This value does not exist in database", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    abstract public void setFirebaseDataChangeNotifier(FirebaseDataChangeNotifier listener);

    abstract public void unsetFirebaseDataChangeNotifier();

}
