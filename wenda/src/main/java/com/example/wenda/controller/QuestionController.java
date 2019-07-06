package com.example.wenda.controller;

import com.example.wenda.model.HostHolder;
import com.example.wenda.model.Question;
import com.example.wenda.service.QuestionService;
import com.example.wenda.service.UserService;
import com.example.wenda.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author zyy
 * @date 2019/7/5 16:19
 */
@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @RequestMapping(path = "/question/add",method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                           @RequestParam("content") String content){
        try {
            Question question = new Question();
            question.setTitle(title);
            question.setContent(content);
            question.setCommentCount(0);
            question.setCreateDate(new Date());

            if(hostHolder.getUser()==null){
                question.setUserId(CommonUtils.ANONYMOUS_UDERID);
            }else{
                question.setUserId(hostHolder.getUser().getId());
            }
            if(questionService.addQuestion(question)>0){
                return CommonUtils.getJsonString(0);
            }

        } catch (Exception e) {
            logger.error("提问失败"+e.getMessage());
        }
        return CommonUtils.getJsonString(1,"失败");
    }

    @RequestMapping(path = "/question/{qid}",method = RequestMethod.GET)
    public String questionDetail(Model model, @PathVariable("qid") int qid){
        Question question=questionService.getquestion(qid);
        model.addAttribute("question",question);
        model.addAttribute("user",userService.usergetById(question.getUserId()));
        return "detail";
    }



}
