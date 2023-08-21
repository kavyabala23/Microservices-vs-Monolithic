package com.example.quizservice.Service;



import com.example.quizservice.DAO.QuizDao;
import com.example.quizservice.Model.QuestionWrapper;
import com.example.quizservice.Model.Quiz;
import com.example.quizservice.Model.Response;
import com.example.quizservice.feign.QuizInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
@Autowired
QuizDao quizDao;
@Autowired
QuizInterface quizInterface;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        //call the generate url -Resttemplate http://localhost:8080/question/generate
        //RestTemplate - instead of this we have used feign

        List<Integer> questions =quizInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

   public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
     Quiz quiz =quizDao.findById(id).get();
     List<Integer> questionIds = quiz.getQuestionIds();

     ResponseEntity<List<QuestionWrapper>>  questions =quizInterface.getQuestionById(questionIds);
     return  questions;





    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
       //calculation is happening in the question we are just requesting it
        ResponseEntity<Integer> score =quizInterface.getScore(responses);
             return score;
    }
}
