package com.example.wenda.dao;

import com.example.wenda.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zyy
 * @date 2019/7/6 20:21
 */
@Mapper
public interface CommentDao {
    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " user_id,content,created_date,entity_id,entity_type,status";
    String SELECT_FIELDS = " id, "+INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,
            ") values(#{userId},#{content},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where entity_id =#{entityId} and entity_type=#{entityType}"})
    List<Comment> selectComment(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select count(id) from ",TABLE_NAME,"where entity_id =#{entityId} and entity_type=#{entityType}"})
    int getComments(@Param("entityId") int entityId,@Param("entityType") int entityType);

    @Update({"update ",TABLE_NAME," set status=#{status} where id=#{commentId}"})
    int updateStatus(@Param("commentId") int commentId,@Param("status") int status );
}
