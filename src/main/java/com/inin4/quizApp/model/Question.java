package com.inin4.quizApp.model;

import java.util.Map;

public class Question {

    private String id;
    private String question;
    private Map<String, String> options;
    private String correctAnswer;

    public Question() {
        // wymagany przez Jacksona
    }

    public Question(String id, String question, Map<String, String> options, String correctAnswer) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public Map<String, String> getOptions() { return options; }
    public void setOptions(Map<String, String> options) { this.options = options; }

    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
}

