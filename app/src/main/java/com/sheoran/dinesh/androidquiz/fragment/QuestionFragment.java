package com.sheoran.dinesh.androidquiz.fragment;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sheoran.dinesh.androidquiz.Adapter.QuestionRecyclerAdapter;
import com.sheoran.dinesh.androidquiz.R;
import com.sheoran.dinesh.androidquiz.model.QuestionOption;
import com.sheoran.dinesh.androidquiz.model.Questions;

import java.util.ArrayList;

public class QuestionFragment extends Fragment {
    private static QuestionFragment _instance;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
    private ArrayList<Questions> questionsArrayList;
    private TextView _textResult;

    private int totalCorrectAnswer;
    private int totalQuestion;
    private DatabaseReference usersReference;
    private Questions questions ;
    public static Fragment getInstance() {
        if (_instance == null) {
            _instance = new QuestionFragment();
        }
        return _instance;
    }

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
        initFirebase();
        loadQuestions();
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalCorrectAnswer = 0;
                for (int i = 0; i < questionsArrayList.size(); i++) {
                    Questions questions = questionsArrayList.get(i);

                    if (questions != null)
                        if (questions.getUserSelected().equals(questions.getRightAnswer())) {
                            totalCorrectAnswer++;
                        }
                }
                _textResult.setText("Correct = " + totalCorrectAnswer + "/" + totalQuestion);
            }
        });

        QuestionRecyclerAdapter adapter = new QuestionRecyclerAdapter(questionsArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = recyclerView.getChildAdapterPosition(view);
                Log.i("posTag", "pos = " + pos);
            }
        });

        return view;
    }


    private void initFirebase() {
        FirebaseApp.initializeApp(getContext());
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        usersReference = firebaseDatabase.getReference("Questions");
    }

    private void loadQuestions() {
        questionsArrayList = new ArrayList<>();

        questionsArrayList.add( getFromFirebase("111"));

        totalQuestion = questionsArrayList.size();
    }

    private Questions getFromFirebase(final String id) {

        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(id).exists()) {
                    questions = dataSnapshot.child(id).getValue(Questions.class);
                } else {
                    Toast.makeText(getContext(), "Id does not existed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("errorTag","error: "+databaseError.getMessage());
                questions = null;
            }
        });

        return questions;
    }

}
