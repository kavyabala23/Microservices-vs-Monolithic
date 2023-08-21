package com.example.QuizApp.Service;


import com.example.QuizApp.DAO.QuestionDao;
import com.example.QuizApp.DAO.QuizDao;
import com.example.QuizApp.Model.Question;
import com.example.QuizApp.Model.QuestionWrapper;
import com.example.QuizApp.Model.Quiz;
import com.example.QuizApp.Model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
@Autowired
QuizDao quizDao;
@Autowired
QuestionDao questionDao;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions =questionDao.findRandomQuestionByCategory(category,numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestion(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

   public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
     Optional<Quiz>quiz =quizDao.findById(id);
     List<Question> questionFromDB = quiz.get().getQuestion();
        //manually converting the question into questionWrapper
     List<QuestionWrapper> questionForUser =new ArrayList<>();
        for(Question q:questionFromDB){
            QuestionWrapper qw= new QuestionWrapper(q.getId(),q.getQuestiontitle(),q.getOption1(),q.getOption2(),q.getOption3());
            questionForUser.add(qw);

        }


        return new ResponseEntity<>(questionForUser,HttpStatus.OK);

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
            Quiz quiz =quizDao.findById(id).get();
             List<Question> questions =quiz.getQuestion();
             int right =0;
             int i=0;
             for(Response response: responses){
                 if(response.getResponse().equals(questions.get(i).getRightanswer()))
                     right++;
                 i++;

             }
             return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
