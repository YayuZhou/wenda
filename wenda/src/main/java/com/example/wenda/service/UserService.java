package com.example.wenda.service;

import com.example.wenda.dao.LoginticketDao;
import com.example.wenda.dao.UserDao;
import com.example.wenda.model.Loginticket;
import com.example.wenda.model.User;
import com.example.wenda.utils.WendaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author zyy
 * @date 2019/7/2 22:47
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginticketDao loginticketDao;
    //注册用户
    public Map<String,String> register(String username,String password){
        Map<String,String> map = new HashMap<>();
        if(StringUtils.isEmpty(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isEmpty(password)){
            map.put("msg","密码不能为空");
            return map;
        }

        User user = userDao.selectByName(username);
        if(user!=null){
            map.put("msg","用户已经被注册");
            return map;
        }
        user = new User();
        user.setName(username);
        //user.setPassword(password);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",
                new Random().nextInt(1000)));
        user.setPassword(WendaUtils.getMD5(user.getSalt()+password));
        userDao.addUser(user);
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    public User usergetById(int id){
        return userDao.selectById(id);
    }
    /*
    登录
     */
    public Map<String,String> login(String username,String password){
        Map<String,String> map = new HashMap<>();
        User user = userDao.selectByName(username);
        if(StringUtils.isEmpty(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isEmpty(password)){
            map.put("msg","密码不能为空");
            return map;
        }
        if(user == null){
            map.put("msg","用户名不存在");
            return map;
        }

        if(!WendaUtils.getMD5(user.getSalt()+password).equals(user.getPassword())){
            map.put("msg","密码错误");
            return map;
        }
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    public String addLoginTicket(int userId){
        Loginticket loginticket=new Loginticket();
        loginticket.setUserId(userId);
        Date now =new Date();
        now.setTime(3600*24*100+now.getTime());
        loginticket.setExpired(now);
        loginticket.setStatus(0);
        loginticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginticketDao.addLoginticket(loginticket);
        return loginticket.getTicket();
    }

    public void logout(String ticket){
        loginticketDao.updateStatus(ticket,1);
    }
}
