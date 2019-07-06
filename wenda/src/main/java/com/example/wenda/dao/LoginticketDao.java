package com.example.wenda.dao;

import com.example.wenda.model.Loginticket;
import org.apache.ibatis.annotations.*;

/**
 * @author zyy
 * @date 2019/7/3 19:25
 */
@Mapper
public interface LoginticketDao {
    String TABLE_NAME = " login_ticket ";
    String INSERT_FIELDS = " user_id,expired,ticket,status";
    String SELECT_FIELDS = " id, "+INSERT_FIELDS;

    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,
            ")values(#{userId},#{expired},#{ticket},#{status})"})
    int addLoginticket(Loginticket loginticket);

    @Select({"select ",SELECT_FIELDS,"from",TABLE_NAME,"where ticket=#{ticket}"})
    Loginticket selectLoginticketByTicket(String ticket);

    @Update({"update ",TABLE_NAME," set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket,@Param("status") int status);
}
