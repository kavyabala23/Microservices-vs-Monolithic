package com.example.quizservice.feign;

import com.example.quizservice.Model.QuestionWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import com.example.quizservice.Model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    //which method to access

    @GetMapping("question/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName , @RequestParam Integer numQuestions);
    @PostMapping("question/getQuestion")
    public ResponseEntity<List<QuestionWrapper>> getQuestionById(@RequestBody List<Integer> questionIds);

    //to calculate score
    @PostMapping("question/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
