package com.example.wenda.dao;
import com.example.wenda.model.Question;
import com.example.wenda.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zyy
 * @date 2019/7/1 11:12
 * 通过注解的方式来进行curd操作
 */
@Mapper
public interface QuestionDao {
    String TABLE_NAME = " question ";
    String INSERT_FIELDS = " title,content,user_id,create_date, comment_count";
    String SELECT_FIELDS = " id, "+INSERT_FIELDS;
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,
            ") values(#{title},#{content},#{userId},#{createDate},#{commentCount})"})
    int addQuestion(Question question);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where id=#{id}"})
    Question selectQuestionById(int qid);

   //xml配置方法
    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                         @Param("offset")int offset,
                                         @Param("limit") int limit);

    @Update({"update question set comment_count = #{commentCount} where id=#{id}"})
    int updateCommentCount(@Param("id") int id,@Param("commentCount") int commentCount);
}
