package com.inin4.quizApp.model;

import java.util.Map;

public class Question {

    private String id;
    private String question;
    private Map<String, String> options; //odpowiedzi do wyboru
    private String correctAnswer;

    public Question(String id, String question, Map<String, String> options, String correctAnswer) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getId() { return id; }
    public String getQuestion() { return question; }
    public Map<String, String> getOptions() { return options; }
    public String getCorrectAnswer() { return correctAnswer; }

    public void setId(String id) { this.id = id; }
    public void setQuestion(String question) { this.question = question; }
    public void setOptions(Map<String, String> options) { this.options = options; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
}
