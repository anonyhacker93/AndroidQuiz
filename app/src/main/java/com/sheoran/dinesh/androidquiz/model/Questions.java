package com.sheoran.dinesh.androidquiz.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Questions implements Parcelable {

    private String id;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String categoryName;
    private String rightAnswer;
    private String userSelected;

    public Questions(){}

    public Questions(Parcel in) {
        this.id = in.readString();
        this.question = in.readString();
        this.option1 = in.readString();
        this.option2 = in.readString();
        this.option3 = in.readString();
        this.option4 = in.readString();
        this.categoryName = in.readString();
        this.rightAnswer = in.readString();
        this.userSelected = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getUserSelected() {
        return userSelected;
    }

    public void setUserSelected(String userSelected) {
        this.userSelected = userSelected;
    }

    public Questions(String id, String question, String option1, String option2, String option3, String option4) {
        this.id = id;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.question);
        parcel.writeString(this.option1);
        parcel.writeString(this.option2);
        parcel.writeString(this.option3);
        parcel.writeString(this.option4);
        parcel.writeString(this.categoryName);
        parcel.writeString(this.rightAnswer);
        parcel.writeString(this.userSelected);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Questions createFromParcel(Parcel in) {
            return new Questions(in);
        }

        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };
}
