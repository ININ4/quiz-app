package com.inin4.quizApp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inin4.quizApp.model.Question;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
public class QuizService {

    private List<Question> questions;

    public QuizService() {
        loadQuestionsFromFile();
    }

    private void loadQuestionsFromFile() {
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = new ClassPathResource("questions.json").getInputStream();
            questions = mapper.readValue(inputStream, new TypeReference<List<Question>>() {});
            
        } catch (Exception e) {
            System.err.println("Błąd podczas wczytywania pytań z pliku:");
            e.printStackTrace();
            questions = new ArrayList<>();
        }
    }

    public List<Question> getAllQuestions() {
        return questions;
    }

    public Optional<Question> getQuestionById(String id) {
        return questions.stream().filter(q -> q.getId().equals(id)).findFirst();
    }

    public boolean checkAnswer(String questionId, String selected) {
        
        return getQuestionById(questionId)
                .map(q -> q.getCorrectAnswer().equalsIgnoreCase(selected))
                .orElse(false);
    }

    public int getTotalQuestions() {
        return questions.size();
    }
}
