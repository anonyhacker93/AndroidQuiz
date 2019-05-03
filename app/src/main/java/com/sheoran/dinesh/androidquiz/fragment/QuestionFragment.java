package com.sheoran.dinesh.androidquiz.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sheoran.dinesh.androidquiz.Adapter.QuestionRecyclerAdapter;
import com.sheoran.dinesh.androidquiz.R;
import com.sheoran.dinesh.androidquiz.dialog.ResultDialogFragment;
import com.sheoran.dinesh.androidquiz.firebase.FirebaseConfigManager;
import com.sheoran.dinesh.androidquiz.firebase.QuestionFirebaseConfig;
import com.sheoran.dinesh.androidquiz.listener.FirebaseDataChangeAdapter;
import com.sheoran.dinesh.androidquiz.listener.FirebaseDataChangeNotifier;
import com.sheoran.dinesh.androidquiz.model.Category;
import com.sheoran.dinesh.androidquiz.model.Questions;
import com.sheoran.dinesh.androidquiz.util.ProgressDialogUtil;

import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends BaseFragment {
    private RecyclerView recyclerView;

    private ArrayList<Questions> _questionsArrayList=new ArrayList<>();

    private ProgressDialog _loadDataProgressDialog;

    private FirebaseConfigManager _firebaseConfigManager;
    private QuestionFirebaseConfig _questionFirebaseConfig;
    private QuestionRecyclerAdapter _adapter;


    private FirebaseDataChangeNotifier firebaseDataChangeNotifier = new FirebaseDataChangeAdapter() {
        @Override
        public void onLoad(boolean isSuccess, String msg) {
            if (_loadDataProgressDialog.isShowing()) {
                _loadDataProgressDialog.dismiss();
                List<Questions> questionsList = _questionFirebaseConfig.getAllQuestionsList();
                updateRecyclerView(questionsList);
            }
        }
    };

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
        resultButton.setOnClickListener((v) -> {
             showResultFragment();

        });

        init();
        setRecyclerAdapter();

        Bundle bundle = getArguments();
        if (bundle != null) {
            Category category = (Category) bundle.getSerializable(CategoriesFragment.CATEGORY_KEY);
            if (category != null) {
                loadQuestions(category.getCategoryName());
            }
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _questionFirebaseConfig.unsetFirebaseDataChangeNotifier();
    }

    private void setRecyclerAdapter() {
        _adapter = new QuestionRecyclerAdapter(getContext(), _questionsArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(_adapter);
    }

    private void showResultFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("QuestionArrayList", _questionsArrayList);
        ResultDialogFragment resultDialogFragment = new ResultDialogFragment();
        resultDialogFragment.setArguments(bundle);
        resultDialogFragment.show(getFragmentManager(), "");
    }

    private void init() {
        _questionsArrayList = new ArrayList<>();


        _firebaseConfigManager = FirebaseConfigManager.getInstance(getContext());
        _questionFirebaseConfig = _firebaseConfigManager.getQuestionConfig();

        _questionFirebaseConfig.setFirebaseDataChangeNotifier(firebaseDataChangeNotifier);
    }

    private void loadQuestions(String categName) {
        _loadDataProgressDialog = ProgressDialogUtil.getDialog(getContext(), "Loading", "Please wait");
        _loadDataProgressDialog.show();

        _questionFirebaseConfig.requestLoadQuestion(categName);
    }

    private void updateRecyclerView(List<Questions> questionsList){
        _questionsArrayList.clear();
        _questionsArrayList.addAll(questionsList);
        _adapter.notifyDataSetChanged();
    }
}
