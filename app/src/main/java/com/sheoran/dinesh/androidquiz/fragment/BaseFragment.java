package com.sheoran.dinesh.androidquiz.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.sheoran.dinesh.androidquiz.util.FirebaseHelper;


public class BaseFragment extends Fragment {

    protected DatabaseReference firebaseDatabaseReference;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }


    private void init(){
    }

    protected void initFirebase(Context context, String databaseReference) {
        FirebaseHelper firebaseHelper = new FirebaseHelper(context,databaseReference);
        firebaseDatabaseReference = firebaseHelper.getDatabaseReference();
    }

    protected void replaceFragment(Fragment fragment,int container) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
