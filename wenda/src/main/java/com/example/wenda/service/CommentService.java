package com.example.wenda.service;

import com.example.wenda.dao.CommentDao;
import com.example.wenda.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zyy
 * @date 2019/7/6 20:34
 */
@Service
public class CommentService {


    @Autowired
    CommentDao commentDao;

    public int addComments(Comment comment){
        return commentDao.addComment(comment)>0?comment.getId():0;
    }

    public List<Comment> selectCommentByEntity(int entityId,int entityType){
        return commentDao.selectComment(entityId,entityType);
    }

    public int selectCommentsByEntity(int entityId,int entityType){
        return commentDao.getComments(entityId,entityType);
    }

    public boolean deleteComment(int commentId,int status){
        return commentDao.updateStatus(commentId,status)>0;
    }


}
