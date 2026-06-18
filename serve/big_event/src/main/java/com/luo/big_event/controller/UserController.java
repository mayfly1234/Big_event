package com.luo.big_event.controller;

import com.luo.big_event.pojo.Result;
import com.luo.big_event.pojo.User;
import com.luo.big_event.service.UserService;
import com.luo.big_event.utils.JwtUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public Result register(
            @Pattern(regexp = "^\\S{5,16}$")  String username,
           @Pattern(regexp = "^\\S{5,16}$")  String password) {

    User u =userService.findByUserName(username);

    if(u == null) {
        userService.register(username,password);
        return Result.success();
    }else{
        return Result.error( "用户名已存在");
    }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        User u = userService.findByUserName(username);

        if(u == null) {
            return Result.error("用户名不存在");
        }else{
            if(DigestUtils.md5DigestAsHex(password.getBytes()).equals(u.getPassword())) {

                Map<String,Object> claims = new HashMap<>();
                claims.put("id",u.getId());
                claims.put("username",u.getUsername());
                String token = JwtUtil.genToken(claims);

                return Result.success(token);
            }else{
                return Result.error("密码错误");
            }
        }
    }


}
