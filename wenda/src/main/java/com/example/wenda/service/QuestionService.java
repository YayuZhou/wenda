package com.example.wenda.service;

import com.example.wenda.dao.QuestionDao;
import com.example.wenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @author zyy
 * @date 2019/7/2 22:47
 */
@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    @Autowired
    SensitiveService sensitiveService;
    public List<Question> getlastestQuestions(int userId,
                                              int offset,int limmit){
        return questionDao.selectLatestQuestions(userId, offset, limmit);
    }

    public int addQuestion(Question question){
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        question.setTitle(sensitiveService.filter(question.getTitle()));
        return questionDao.addQuestion(question)>0?question.getUserId():0;
    }
    public Question getquestion(int id){
        return questionDao.selectQuestionById(id);
    }

    public int getCommentCounts(int qid,int commentCounts){
        return questionDao.updateCommentCount(qid,commentCounts);
    }


}
