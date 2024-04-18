package com.example.a1201952_todo1;

public class TrueFalseQuestion extends Question{
    public TrueFalseQuestion(String qText, boolean correctAnswer) {
        super(qText, new String[]{"True", "False"}, correctAnswer ? 0 : 1);
    }
}


