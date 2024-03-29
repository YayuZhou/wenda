package com.example.wenda;

import com.example.wenda.dao.QuestionDao;
import com.example.wenda.dao.UserDao;
import com.example.wenda.model.Question;
import com.example.wenda.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = WendaApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDao userDao;

    @Autowired
    QuestionDao questionDao;
    Random random = new Random();
    @Test
    public void initDatabase() {
        for(int i=0;i<10;i++){
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
            user.setName(String.format("USER%d",i));
            user.setPassword("");
            user.setSalt("");
            userDao.addUser(user);

            user.setPassword("xxx");
            userDao.updatePassword(user);

            Question question = new Question();
            question.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime()+1000*3600*i);
            question.setCreateDate(date);
            question.setUserId(i+1);
            question.setTitle(String.format("Title{%d}",i));
            question.setContent(String.format("Balallalla Content %d",i));
            questionDao.addQuestion(question);

        }
        Assert.assertEquals("xxx",userDao.selectById(1).getPassword());
//        userDao.deleteById(1);
        Assert.assertNull(userDao.selectById(1));

        questionDao.selectLatestQuestions(0,0,10);
    }

}

