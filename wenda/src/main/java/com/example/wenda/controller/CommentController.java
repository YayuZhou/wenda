package com.example.wenda.controller;

import com.example.wenda.model.Comment;
import com.example.wenda.model.EntityType;
import com.example.wenda.model.HostHolder;
import com.example.wenda.service.CommentService;
import com.example.wenda.service.QuestionService;
import com.example.wenda.service.SensitiveService;
import com.example.wenda.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

/**
 * @author zyy
 * @date 2019/7/6 20:38
 */
@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;

    @Autowired
    SensitiveService sensitiveService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    QuestionService questionService;

    @RequestMapping(path="/addComment",method = RequestMethod.POST)
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content){
        try {
            Comment comment = new Comment();
            content = HtmlUtils.htmlEscape(content);
            content = sensitiveService.filter(content);
            comment.setContent(content);
            if(hostHolder.getUser()==null){
                comment.setUserId(CommonUtils.ANONYMOUS_UDERID);
            }else{
                comment.setUserId(hostHolder.getUser().getId());
            }
            comment.setCreatedTime(new Date());
            comment.setEntityId(questionId);
            comment.setEntityType(EntityType.ENTITY_COMMENT);
            comment.setStatus(0);

            commentService.addComments(comment);
            //更新问题下的评论数量
            int count = commentService.selectCommentsByEntity(comment.getEntityId(),comment.getEntityType());
            questionService.getCommentCounts(comment.getEntityId(),count);

        } catch (Exception e) {
            logger.error("提问失败"+e.getMessage());

        }
        return "redirect:/question/"+questionId;
    }
}

