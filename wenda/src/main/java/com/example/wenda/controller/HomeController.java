package com.example.wenda.controller;

import com.example.wenda.model.Question;
import com.example.wenda.model.ViewObject;
import com.example.wenda.service.QuestionService;
import com.example.wenda.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 * @author zyy
 * @date 2019/7/2 15:55
 */
@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;


    @RequestMapping(path = {"/user/{userId}"},method = RequestMethod.GET)
    public String Userndex(Model model, @PathVariable("userId") int userId){
        model.addAttribute("vos",getQusetions(userId,0,10));
        return "index";
    }


    @RequestMapping(path = {"/","/index"},method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("vos",getQusetions(0,0,10));
        return "index";
    }

    private List<ViewObject> getQusetions(int userId,int offset,int limit){
        List<Question> questionList = questionService.getlastestQuestions(userId,offset,limit);
        List<ViewObject> vos = new ArrayList<>();
        for(Question question:questionList){
            ViewObject vo = new ViewObject();
            vo.set("question",question);
            vo.set("user",userService.usergetById(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }

}
