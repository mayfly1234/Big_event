package com.luo.big_event.interceptors;

import com.luo.big_event.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String token =request.getHeader("Authorization");
        try{
            Map<String,Object> chaims= JwtUtil.parseToken(token);
            return true;
        }catch (Exception e){
            response.setStatus(401);
            return false;
        }
    }
}
