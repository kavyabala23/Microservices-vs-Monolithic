package com.example.QuizApp.Controller;

import com.example.QuizApp.DAO.QuestionDao;
import com.example.QuizApp.Model.Question;
import com.example.QuizApp.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
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
}
