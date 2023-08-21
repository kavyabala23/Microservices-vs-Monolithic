package com.example.questionservice.Controller;

import com.example.questionservice.Model.Question;
import com.example.questionservice.Model.QuestionWrapper;
import com.example.questionservice.Model.Response;
import com.example.questionservice.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("allQuestion")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestion();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>>  getQuestionByCategory(@PathVariable String category){
        return questionService.getQuestionByCategory(category);

    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
       return questionService.addQuestion(question);
    }

    @DeleteMapping("remove/{id}")
    public ResponseEntity<String> removeQuestion(@PathVariable Integer id){
        return questionService.removeQuestion(id);
    }

    //In first project the quiz has the access to question database but in this it does not have access to this, so we have to create a method that will deal with the quiz request
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName ,@RequestParam Integer numQuestions){
        return questionService.getQuestionsForQuiz(categoryName,numQuestions);

    }

    //Will return list of question without right answer
    @PostMapping("getQuestion")
    public  ResponseEntity<List<QuestionWrapper>> getQuestionById(@RequestBody List<Integer> questionIds){
        System.out.print(environment.getProperty("local.server.port"));
        return questionService.getQuestionFromId(questionIds);
    }

    //to calculate score
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }

}
