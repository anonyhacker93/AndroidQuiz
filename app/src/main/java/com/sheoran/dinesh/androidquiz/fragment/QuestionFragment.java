package com.sheoran.dinesh.androidquiz.fragment;

import android.os.Bundle;
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
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalCorrectAnswer = 0;
                for (int i = 0; i < questionsArrayList.size(); i++) {
                    Questions questions = questionsArrayList.get(i);

                    if(questions!=null)
                    if(questions.getUserSelected().equals(questions.getRightOption())){
                        totalCorrectAnswer++;
                    }
                }
                _textResult.setText("Correct = "+totalCorrectAnswer+"/"+totalQuestion);
            }
        });
        loadQuestions();
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

    private void loadQuestions() {
        questionsArrayList = new ArrayList<>();
        QuestionOption options = new QuestionOption();
        options.setOptions(new String[]{"US", "Japan", "Russia", "India"});
        Questions questions = new Questions();
        questions.setQuestion("Who is super power country ?");
        questions.setOptions(options);
        questions.setRightOption(options.getOptions()[0]);

        QuestionOption options2 = new QuestionOption();
        options2.setOptions(new String[]{"Rupee", "Japan Dollar", "Yen", "Pond"});
        Questions questions2 = new Questions();
        questions2.setQuestion("What is Japan Currency ?");
        questions2.setOptions(options2);
        questions2.setRightOption(options2.getOptions()[2]);


        QuestionOption options3 = new QuestionOption();
        options3.setOptions(new String[]{"Delhi", "Singapore", "Thailand", "Moscow"});
        Questions questions3 = new Questions();
        questions3.setQuestion("What is capital of Rusia ?");
        questions3.setOptions(options3);
        questions3.setRightOption(options3.getOptions()[3]);


        QuestionOption options4 = new QuestionOption();
        options4.setOptions(new String[]{"India", "Sri-Lanka", "Australia", "England"});
        Questions questions4 = new Questions();
        questions4.setQuestion("Who won the 2018 Women T20 Word Cup");
        questions4.setOptions(options4);
        questions4.setRightOption(options4.getOptions()[2]);


        questionsArrayList.add(questions);
        questionsArrayList.add(questions2);
        questionsArrayList.add(questions3);
        questionsArrayList.add(questions4);

        totalQuestion = questionsArrayList.size();
    }

}
