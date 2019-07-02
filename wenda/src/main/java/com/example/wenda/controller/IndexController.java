package com.example.wenda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * @author zyy
 * @date 2019/6/29 12:54
 */
//@Controller
public class IndexController {
    @RequestMapping(path={"/","/index"})
//    //ResponseBody 返回文本
    @ResponseBody
    public String index(HttpSession httpSession){
        return "Hello NowCoder"+httpSession.getAttribute("msg");
    }
//路径参数通过pathvariable 解析到变量中
    @RequestMapping(path={"/profile/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @RequestParam("type") int type,
                          @RequestParam("key") String key){
        return String.format("Profile page of %d,t:%d,k:%s",userId,type,key);
    }

    @RequestMapping(path ={"/vm"},method = {RequestMethod.GET})
    public String template(Model model){
        model.addAttribute("value1","dasdsa");
        return "home";
    }


    @RequestMapping(path ={"/request"},method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model, HttpServletResponse response,
                          HttpServletRequest request,
                          HttpSession httpSession){
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            sb.append(name+":"+request.getHeader(name)+"<br>");
        }
        sb.append(request.getMethod()+"<br>");
        sb.append(request.getQueryString()+"<br>");
        sb.append(request.getPathInfo()+"<br>");
        sb.append(request.getCookies()+"<br>");


        response.addHeader("nowCoderid","hello");
        return sb.toString();

    }

    @RequestMapping(path = {"/redirect/{code}"},method = RequestMethod.GET)
    public RedirectView redirect(@PathVariable("code") int code,
                           HttpSession httpSession){
        httpSession.setAttribute("msg","jump from redirect");
        RedirectView red = new RedirectView("/",true);
        if(code==301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }
    @RequestMapping(path = {"/admin"},method = RequestMethod.GET)
    @ResponseBody
    public String admin(@RequestParam("key") String key){
        if("admin".equals(key)){
            return "hello admin";
        }
        throw new IllegalArgumentException("参数不对");
    }
    @ExceptionHandler
    @ResponseBody
    public String error(Exception e){
        return "error:"+e.getMessage();
    }

}
