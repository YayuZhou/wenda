package com.example.wenda.interceptor;

import com.example.wenda.dao.LoginticketDao;
import com.example.wenda.dao.UserDao;
import com.example.wenda.model.HostHolder;
import com.example.wenda.model.Loginticket;
import com.example.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author zyy
 * @date 2019/7/4 11:14
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    LoginticketDao loginticketDao;

    @Autowired
    UserDao userDao;

    @Autowired
    HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = null;
        if(request.getCookies()!=null) {
            for (Cookie cookie : request.getCookies()) {
                if(cookie.getName().equals("ticket")){
                    ticket=cookie.getValue();
                    break;
                }
            }
        }
        if(ticket!=null){
            Loginticket loginticket=loginticketDao.selectLoginticketByTicket(ticket);
            if(loginticket==null||loginticket.getExpired().before(new Date())||loginticket.getStatus()!=0){
                return true;
            }

            User user =userDao.selectById(loginticket.getUserId());
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(modelAndView!=null&& hostHolder.getUser() != null){
            modelAndView.addObject("user",hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
