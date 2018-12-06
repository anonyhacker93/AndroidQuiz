package com.sheoran.dinesh.androidquiz.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sheoran.dinesh.androidquiz.Adapter.QuestionRecyclerAdapter;
import com.sheoran.dinesh.androidquiz.R;
import com.sheoran.dinesh.androidquiz.dialog.ResultDialogFragment;
import com.sheoran.dinesh.androidquiz.model.Category;
import com.sheoran.dinesh.androidquiz.model.Questions;
import com.sheoran.dinesh.androidquiz.util.Constants;

import java.util.ArrayList;
import java.util.Iterator;

public class QuestionFragment extends BaseFragment {
    private RecyclerView recyclerView;

    private ArrayList<Questions> questionsArrayList;
    private ArrayList<Category> _categoryArrayList;

    private QuestionRecyclerAdapter _adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, null, false);
        recyclerView = view.findViewById(R.id.question_recyler);
        Button resultButton = view.findViewById(R.id.resultButton);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Category category = (Category) bundle.getSerializable(CategoriesFragment.CATEGORY_KEY);
            if (category != null) {
                loadQuestions(category.getCategoryName());
            }
        }

        initFirebase(getContext(), Constants.FIREBASE_QUESTION_REFERENCE);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("QuestionArrayList", questionsArrayList);
                ResultDialogFragment resultDialogFragment = new ResultDialogFragment();
                resultDialogFragment.setArguments(bundle);
                resultDialogFragment.show(getFragmentManager(), "");
            }
        });
        loadCategories();
        questionsArrayList = new ArrayList<>();
        _categoryArrayList = new ArrayList<>();
        _adapter = new QuestionRecyclerAdapter(getContext(),questionsArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(_adapter);

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = recyclerView.getChildAdapterPosition(view);
                Log.i("posTag", "pos = " + pos);
            }
        });

        return view;
    }

    private void loadQuestions(final String categName) {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading Data");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        DatabaseReference databaseReference = initFirebase(getContext(), Constants.FIREBASE_QUESTION_REFERENCE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.child(categName).getChildren().iterator();
                Questions questions;
                questionsArrayList.clear();
                while (iterator.hasNext()) {
                    DataSnapshot dataShot = iterator.next();
                    questions = dataShot.getValue(Questions.class);
                    questionsArrayList.add(questions);
                }
                _adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("AndroidQuizTag : ", "Exception : " + databaseError.getMessage());
                Toast.makeText(getContext(), "Exception : " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }

    private ArrayList<Category> loadCategories() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading categories");
        progressDialog.setMessage("Please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

        final ArrayList<Category> categoryArrayList = new ArrayList<>();
        DatabaseReference reference = initFirebase(getContext(), Constants.FIREBASE_CATEGORY_REF);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot ds) {
                Iterator<DataSnapshot> itr = ds.getChildren().iterator();
                while (itr.hasNext()) {
                    DataSnapshot shot = itr.next();
                    Category category = shot.getValue(Category.class);
                    if (category != null) {
                        categoryArrayList.add(category);
                    }
                }
                _categoryArrayList.addAll(categoryArrayList);
                String[] dataList;
                dataList = new String[categoryArrayList.size() + 1];
                dataList[0] = "Categories";
                if (categoryArrayList != null) {
                    int indx = 1;
                    for (Category categ : categoryArrayList) {
                        dataList[indx] = categ.getCategoryName();
                        indx++;
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Unable to access database !", Toast.LENGTH_SHORT).show();
            }
        });
        return categoryArrayList;
    }

}
