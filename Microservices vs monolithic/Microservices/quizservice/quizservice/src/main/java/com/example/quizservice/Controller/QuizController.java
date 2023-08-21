package com.example.quizservice.Controller;


import com.example.quizservice.Model.QuizDto;
import com.example.quizservice.Service.QuizService;
import com.example.quizservice.Model.QuestionWrapper;
import com.example.quizservice.Model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;



    @PostMapping("create")
    //Dto - Data Transfer Object
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
       return quizService.createQuiz(quizDto.getCategoryName(),quizDto.getNumQuestion(),quizDto.getTitle());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
       return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id,@RequestBody List<Response> response){
        return quizService.calculateResult(id,response);
    }

}
