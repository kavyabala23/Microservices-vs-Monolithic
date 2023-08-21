package com.example.questionservice.Service;

import com.example.questionservice.DAO.QuestionDao;

import com.example.questionservice.Model.Question;
import com.example.questionservice.Model.QuestionWrapper;
import com.example.questionservice.Model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;


    public ResponseEntity<List<Question>> getAllQuestion() {

        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
       questionDao.save(question);

        return  new ResponseEntity<>("Success",HttpStatus.CREATED);
    }

    public ResponseEntity<String> removeQuestion(Integer id) {
        questionDao.deleteAllById(Collections.singleton(id));
        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        List<Integer> questions =questionDao.findRandomQuestionByCategory(categoryName,numQuestions);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }



    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question>  questions =new ArrayList<>();
        //connect to dao
        //we are getting the question data first
        for(Integer id: questionIds){
            questions.add(questionDao.findById(id).get());
        }

        //Copying the question data to questionwrapper
        for(Question question :questions){
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestiontitle(question.getQuestiontitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());

            wrappers.add(wrapper);
        }
        return new ResponseEntity<>(wrappers,HttpStatus.OK);

    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int right =0;
        for(Response response: responses){
            //From the dao we are getting the single question id
            Question question = questionDao.findById(response.getId()).get() ;
            if(response.getResponse().equals(question.getRightanswer()))
                right++;

        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
