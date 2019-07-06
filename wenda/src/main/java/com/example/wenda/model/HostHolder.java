package com.example.wenda.model;

import org.springframework.stereotype.Component;

/**
 * @author zyy
 * @date 2019/7/4 11:30
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();
    public  User getUser(){
        return users.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}
