package com.inin4.quizApp.controller;

import com.inin4.quizApp.model.Question;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class QuizController {

    @GetMapping("/question")
    public Question getSampleQuestion() {

        return new Question(
                "q1",
                "What is the output of System.out.println(\"Hello World\")?",
                Map.of(
                        "A", "Hello World",
                        "B", "hello world",
                        "C", "Syntax Error"
                ),
                "A"
        );
    }
}
