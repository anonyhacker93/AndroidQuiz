package com.sheoran.dinesh.androidquiz.model;

import java.io.Serializable;

public class PracticeResult{
    private String categoryName;
    private int totalQuestion;
    private int totalRight;
    private int totalAttempt;
    private int totalWrong;

    public PracticeResult() {
    }

    public PracticeResult(String categoryName, int totalQuestion, int totalRight, int totalAttempt, int totalWrong) {
        this.categoryName = categoryName;
        this.totalQuestion = totalQuestion;
        this.totalRight = totalRight;
        this.totalAttempt = totalAttempt;
        this.totalWrong = totalWrong;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public int getTotalRight() {
        return totalRight;
    }

    public void setTotalRight(int totalRight) {
        this.totalRight = totalRight;
    }

    public int getTotalAttempt() {
        return totalAttempt;
    }

    public void setTotalAttempt(int totalAttempt) {
        this.totalAttempt = totalAttempt;
    }

    public int getTotalWrong() {
        return totalWrong;
    }

    public void setTotalWrong(int totalWrong) {
        this.totalWrong = totalWrong;
    }
}
