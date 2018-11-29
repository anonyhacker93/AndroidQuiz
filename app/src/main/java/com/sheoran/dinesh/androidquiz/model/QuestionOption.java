package com.sheoran.dinesh.androidquiz.model;

public class QuestionOption {
    private String options[];

    public QuestionOption() {
    options = new String[4];
    }

    public void setOptions(String options[]){
        this.options = options;
    }

    public String[] getOptions(){
        return options;
    }
}
