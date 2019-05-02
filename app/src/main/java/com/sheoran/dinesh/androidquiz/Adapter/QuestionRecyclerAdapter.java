package com.sheoran.dinesh.androidquiz.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sheoran.dinesh.androidquiz.R;
import com.sheoran.dinesh.androidquiz.databinding.QuestionRecylerHeaderBinding;
import com.sheoran.dinesh.androidquiz.model.Questions;

import java.util.ArrayList;

import static android.content.Context.VIBRATOR_SERVICE;

public class QuestionRecyclerAdapter extends RecyclerView.Adapter<QuestionRecyclerAdapter.MyViewHolder> {
    private ArrayList<Questions> _questionsArrayList;
    private Context _context;

    public QuestionRecyclerAdapter(Context context, ArrayList<Questions> questionsArrayList) {
        _questionsArrayList = questionsArrayList;
        this._context = context;
    }

    @Override
    public QuestionRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        QuestionRecylerHeaderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.question_recyler_header, parent, false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Questions questions = _questionsArrayList.get(position);
        holder.binding.setQuestion(questions);


        holder.binding.optionRadioGroup.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
            RadioButton radioButton = group.findViewById(checkedId);
            questions.setUserSelected(radioButton.getText().toString());
            setRadioButtonDisable(group);

            if (questions.getRightAnswer().equals(questions.getUserSelected())) {
                radioButton.setTextColor(Color.GREEN);
            } else {
                if (!questions.getUserSelected().isEmpty()) {
                    radioButton.setTextColor(Color.RED);
                    vibrate();
                    RadioButton radioBtn;
                    for (int i = 0; i < group.getChildCount(); i++) {
                        radioBtn = (RadioButton) group.getChildAt(i);
                        if (questions.getRightAnswer().equals(radioBtn.getText().toString())) {
                            radioBtn.setTextColor(Color.GREEN);
                        }
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return _questionsArrayList.size();
    }

    private void setRadioButtonDisable(RadioGroup radioGroup) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton rb = (RadioButton) radioGroup.getChildAt(i);
            rb.setClickable(false);
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        QuestionRecylerHeaderBinding binding;

        public MyViewHolder(QuestionRecylerHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void vibrate() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) _context.getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, 10));
        } else {
            ((Vibrator) _context.getSystemService(VIBRATOR_SERVICE)).vibrate(150);
        }
    }
}
