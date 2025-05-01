package com.inin4.quizApp.controller;

import com.inin4.quizApp.model.*;
import com.inin4.quizApp.service.QuizService;
import com.inin4.quizApp.service.ResultService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;
    private final ResultService resultService;

    public QuizController(QuizService quizService, ResultService resultService) {
        this.quizService = quizService;
        this.resultService = resultService;
    }


    @GetMapping("/all")
    public List<Question> getAllQuestions() {
        return quizService.getAllQuestions();
    }

    @GetMapping("/question/{id}")
    public Question getQuestionById(@PathVariable String id) {
        return quizService.getQuestionById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
    }

    @PostMapping("/answer")
    public AnswerResponse submitAnswer(@RequestBody AnswerRequest request) {
        boolean isCorrect = quizService.checkAnswer(request.getQuestionId(), request.getSelectedAnswer());
        String correct = quizService.getQuestionById(request.getQuestionId())
                .map(Question::getCorrectAnswer)
                .orElse("N/A");
        return new AnswerResponse(isCorrect, correct);
    }

    @GetMapping("/count")
    public int totalQuestions() {
        return quizService.getTotalQuestions();
    }

    @PostMapping("/result")
    public void submitResult(@RequestBody Result result) {
        result.setTimestamp(LocalDateTime.now());
        resultService.saveResult(result);
    }

    @GetMapping("/results")
    public List<Result> getResults() {
        return resultService.getAllResults();
    }

}

