package com.luo.big_event.controller;

import com.luo.big_event.pojo.Result;
import com.luo.big_event.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

public class ArticleController {
    public Result<String>list(@RequestHeader(name = "Authorization")String token, HttpServletResponse respon){
        try{
            Map<String,Object> chaims= JwtUtil.parseToken(token);
            return Result.success("欢迎"+chaims.get("username")+"所有的文章数据...");
        }catch (Exception e){
            respon.setStatus(401);
            return Result.error("未登录");
        }
    }
}
