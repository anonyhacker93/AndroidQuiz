package com.sheoran.dinesh.androidquiz.model;

public class Questions {
private String question;
private QuestionOption options;
private String rightOption;
private String userSelected="";

    public Questions() {
        options = new QuestionOption();
    }

    public Questions(String question, QuestionOption options, String rightOption) {
        this.question = question;
        this.options = options;
        this.rightOption = rightOption;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public QuestionOption getOptions() {
        return options;
    }

    public void setOptions(QuestionOption options) {
        this.options = options;
    }

    public String getRightOption() {
        return rightOption;
    }

    public void setRightOption(String rightOption) {
        this.rightOption = rightOption;
    }

    public String getUserSelected() {
        return userSelected;
    }

    public void setUserSelected(String userSelected) {
        this.userSelected = userSelected;
    }
}
