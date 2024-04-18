package com.example.a1201952_todo1;

//This class represant Quetion it self such that we have Two type of quetion
public abstract class Question {
    private String qText; // Question text
    private String[] qOptions; // Question Options
    private int correctAnswerIdx; // Correct answer index from qOptions
    private String userAnswer; // User's answer of the question
    private int userScore; // user's score out of 1 in the question

    public Question(String qText, String[] qOptions, int correctAnswerIdx) {
        this.qText = qText;
        this.qOptions = qOptions;
        this.correctAnswerIdx = correctAnswerIdx;
    }

    // getters
    public String getqText() {
        return qText;
    }

    public String[] getqOptions() {
        return qOptions;
    }

    public int getCorrectAnswerIdx() {
        return correctAnswerIdx;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public int getUserScore(){
        return this.userScore;
    }

    // setters
    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public void setUserScore(){
        this.userScore = this.isUserAnswerCorrect() ? 1 : 0;
    }

    // check correct answer with user answer
    public boolean isUserAnswerCorrect(){
        return this.userAnswer.equals(this.qOptions[this.correctAnswerIdx]);
    }




}
