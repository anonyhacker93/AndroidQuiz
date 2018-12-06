package com.sheoran.dinesh.androidquiz.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.sheoran.dinesh.androidquiz.R;
import com.sheoran.dinesh.androidquiz.model.PracticeResult;
import com.sheoran.dinesh.androidquiz.model.Questions;

import java.util.ArrayList;

public class ResultDialogFragment extends DialogFragment {
    private PieChart pieChart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_fragment_result , container, false);
        pieChart = view.findViewById(R.id.idPieChart);
        initPieChart();

        if(getArguments() !=null){
            Bundle bundle = getArguments();
            ArrayList<Questions> questionsArrayList =  bundle.getParcelableArrayList("QuestionArrayList");
            if(questionsArrayList != null){
                PracticeResult practiceResult = calculateResult(questionsArrayList);
                addDataSetToChart(practiceResult);
            }
        }

        return view;
    }

    private PracticeResult calculateResult(ArrayList<Questions> questionsArrayList){
     PracticeResult practiceResult = new PracticeResult();

        int rightCount = 0;
        int wrongCount = 0;
        int attemptCount = 0;
        Questions questions;
     for(int i=0;i<questionsArrayList.size();i++){
         questions = questionsArrayList.get(i);
         if(questions.getUserSelected().equals(questions.getRightAnswer())){
             rightCount++;
         }else {
             if(questions.getUserSelected().isEmpty()){
                 attemptCount++;
             }else {
                 wrongCount++;
             }
         }
     }

        practiceResult.setTotalQuestion(questionsArrayList.size());
        practiceResult.setTotalAttempt(attemptCount);
        practiceResult.setTotalRight(rightCount);
        practiceResult.setTotalWrong(wrongCount);
        practiceResult.setCategoryName(questionsArrayList.get(0).getCategoryName());
     return practiceResult;
    }

    private void initPieChart() {
        pieChart.setDescription("Result");
        pieChart.setRotationEnabled(true);
        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Super Cool Chart");
        pieChart.setCenterTextSize(8);
    }


    private void addDataSetToChart(PracticeResult practiceResult) {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        yEntrys.add(new PieEntry(practiceResult.getTotalAttempt(), 0));
        yEntrys.add(new PieEntry(practiceResult.getTotalRight(), 1));
        yEntrys.add(new PieEntry(practiceResult.getTotalWrong(), 2));

        xEntrys.add("Total Attempt");
        xEntrys.add("Right Answer");
        xEntrys.add("Wrong Answer");

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.CYAN);
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Result");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        return dialog;
    }
}
