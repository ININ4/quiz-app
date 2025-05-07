package com.inin4.quizApp.model;

public class AnswerResponse {

    private boolean correct;
    private String correctAnswer;

    public AnswerResponse(boolean correct, String correctAnswer) {
        this.correct = correct;
        this.correctAnswer = correctAnswer;
    }

    public boolean isCorrect() { return correct; }
    public String getCorrectAnswer() { return correctAnswer; }
}
