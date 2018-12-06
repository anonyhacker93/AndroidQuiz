package com.sheoran.dinesh.androidquiz.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sheoran.dinesh.androidquiz.R;
import com.sheoran.dinesh.androidquiz.model.Questions;

import java.util.ArrayList;

import static android.content.Context.VIBRATOR_SERVICE;

public class QuestionRecyclerAdapter extends RecyclerView.Adapter<QuestionRecyclerAdapter.MyViewHolder> {
    private ArrayList<Questions> _questionsArrayList;
    private Context _context;
    public QuestionRecyclerAdapter(Context context,ArrayList<Questions> questionsArrayList) {
        _questionsArrayList = questionsArrayList;
        this._context = context;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Questions questions = _questionsArrayList.get(position);
        if (questions == null)
            return;

        holder.question.setText(questions.getQuestion());
        holder.rb1.setText(questions.getOption1());
        holder.rb2.setText(questions.getOption2());
        holder.rb3.setText(questions.getOption3());
        holder.rb4.setText(questions.getOption4());
        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                questions.setUserSelected(rb.getText().toString());
                setRadioButtonDisable(group);

                if (questions.getRightAnswer().equals(questions.getUserSelected())) {
                    rb.setTextColor(Color.GREEN);
                } else {
                    if (questions.getUserSelected().isEmpty()) {

                    } else {
                        rb.setTextColor(Color.RED);
                        vibrate();
                        RadioButton radioButton;
                        for (int i = 0; i < group.getChildCount(); i++){
                            radioButton = (RadioButton) group.getChildAt(i);
                            if(questions.getRightAnswer().equals(radioButton.getText().toString())){
                                radioButton.setTextColor(Color.GREEN);
                            }
                        }
                    }
                }
            }
        });

    }


    @Override
    public QuestionRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_recyler_header, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return _questionsArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void setRadioButtonDisable(RadioGroup radioGroup) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton rb = (RadioButton) radioGroup.getChildAt(i);
            rb.setClickable(false);
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView question;
        public RadioButton rb1, rb2, rb3, rb4;
        public RadioGroup radioGroup;

        public MyViewHolder(View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.text_question);
            radioGroup = itemView.findViewById(R.id.optionRG);
            rb1 = itemView.findViewById(R.id.rb_option1);
            rb2 = itemView.findViewById(R.id.rb_option2);
            rb3 = itemView.findViewById(R.id.rb_option3);
            rb4 = itemView.findViewById(R.id.rb_option4);
            getAdapterPosition();
        }

    }

    private void vibrate() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) _context.getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150,10));
        } else {
            ((Vibrator) _context.getSystemService(VIBRATOR_SERVICE)).vibrate(150);
        }
    }
}
