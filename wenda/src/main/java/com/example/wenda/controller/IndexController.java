package com.example.wenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author zyy
 * @date 2019/6/29 12:54
 */
@Controller

public class IndexController {
    @RequestMapping(path={"/","/index"})
    //ResponseBody 返回文本
    @ResponseBody
    public String index(){
        return "Hello world";
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

}
