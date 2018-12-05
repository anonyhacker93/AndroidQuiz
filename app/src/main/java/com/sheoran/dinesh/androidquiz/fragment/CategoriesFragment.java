package com.sheoran.dinesh.androidquiz.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sheoran.dinesh.androidquiz.Adapter.CategoriesRecyclerAdapter;
import com.sheoran.dinesh.androidquiz.R;
import com.sheoran.dinesh.androidquiz.listener.CategoryRecyclerClickListener;
import com.sheoran.dinesh.androidquiz.model.Category;
import com.sheoran.dinesh.androidquiz.util.Constants;

import java.util.ArrayList;
import java.util.Iterator;

public class CategoriesFragment extends BaseFragment implements CategoryRecyclerClickListener{
    public static final String CATEGORY_KEY = "com.sheoran.dinesh.androidquiz.fragment.CategoriesFragment";
    private RecyclerView _recyclerView;
    private CategoriesRecyclerAdapter _recyclerAdapter;
    private ArrayList<Category> _categoryArrayList;

    public CategoriesFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_view, null, false);
        _recyclerView = view.findViewById(R.id.categoryDisplayRecycler);
        init();
        _recyclerAdapter = new CategoriesRecyclerAdapter(getContext(), (CategoryRecyclerClickListener) this,_categoryArrayList);
        _recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        _recyclerView.setAdapter(_recyclerAdapter);
        return view;
    }



    private void init() {
        _categoryArrayList = new ArrayList<>();
        loadCategory();
    }

    private void loadCategory() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading Data");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        final DatabaseReference reference = initFirebase(getContext(), Constants.FIREBASE_CATEGORY_REF);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                Category category;
                _categoryArrayList.clear();
                while (iterator.hasNext()) {
                    DataSnapshot dataShot = iterator.next();
                    category = dataShot.getValue(Category.class);
                    _categoryArrayList.add(category);
                }
                Log.i(Constants.LOG_TAG,"size : "+_categoryArrayList.size());
                _recyclerAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("QuizAdminTag : ", "Exception : " + databaseError.getMessage());
                Toast.makeText(getContext(), "Exception : " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onSingleClickListener(String str) {
        Bundle bundle =new Bundle();
        bundle.putSerializable(CATEGORY_KEY,new Category(str));
        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setArguments(bundle);
        replaceFragment(questionFragment,R.id.container);
    }
}
