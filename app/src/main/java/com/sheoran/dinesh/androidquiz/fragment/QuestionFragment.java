package com.sheoran.dinesh.androidquiz.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sheoran.dinesh.androidquiz.Adapter.QuestionRecyclerAdapter;
import com.sheoran.dinesh.androidquiz.R;
import com.sheoran.dinesh.androidquiz.model.Category;
import com.sheoran.dinesh.androidquiz.model.Questions;
import com.sheoran.dinesh.androidquiz.util.Constants;

import java.util.ArrayList;
import java.util.Iterator;

public class QuestionFragment extends BaseFragment {
    private static QuestionFragment _instance;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
    private ArrayList<Questions> questionsArrayList;
    private ArrayList<Category> _categoryArrayList;
    private TextView _textResult;
    private Spinner _spinnerCategory;
    private int totalCorrectAnswer;
    private int totalQuestion;
    private DatabaseReference usersReference;
    private Questions questions;
    ArrayAdapter<String> categoryDropdownAdapter;
    public static Fragment getInstance() {
        if (_instance == null) {
            _instance = new QuestionFragment();
        }
        return _instance;
    }

    private QuestionRecyclerAdapter _adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        totalQuestion = 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, null, false);
        recyclerView = view.findViewById(R.id.question_recyler);
        Button resultButton = view.findViewById(R.id.resultButton);
        _textResult = view.findViewById(R.id.txtResult);
        _spinnerCategory = view.findViewById(R.id.spinnerCategory);
        _spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) return;
                loadQuestions(_categoryArrayList.get(i - 1).getCategoryName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        initFirebase(getContext(), Constants.FIREBASE_QUESTION_REFERENCE);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalCorrectAnswer = 0;
                for (int i = 0; i < questionsArrayList.size(); i++) {
                    Questions questions = questionsArrayList.get(i);

                    if (questions != null && questions.getUserSelected() != null)
                        if (questions.getUserSelected().equals(questions.getRightAnswer())) {
                            totalCorrectAnswer++;
                        }
                }
                _textResult.setText("Correct = " + totalCorrectAnswer + "/" + totalQuestion);
            }
        });
        loadCategories();
        questionsArrayList = new ArrayList<>();
        _categoryArrayList = new ArrayList<>();
        _adapter = new QuestionRecyclerAdapter(questionsArrayList);
        recyclerView.setLayoutManager(layoutManager);
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
                categoryDropdownAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dataList);
                categoryDropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                _spinnerCategory.setAdapter(categoryDropdownAdapter);
               /* if (categoryArrayList.size() > 0) {
                    loadQuestions(categoryArrayList.get(0).getCategoryName());
                }*/
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
