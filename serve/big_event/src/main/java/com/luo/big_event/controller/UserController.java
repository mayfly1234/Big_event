package com.luo.big_event.controller;

import com.luo.big_event.pojo.Result;
import com.luo.big_event.pojo.User;
import com.luo.big_event.service.UserService;
import com.luo.big_event.utils.JwtUtil;
import com.luo.big_event.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name="Authorization") String token) {
    /*Map<String,Object>map= JwtUtil.parseToken(token);
    String username = (String)map.get("username");*/
    Map<String,Object> map = ThreadLocalUtil.get();
    String username = (String)map.get("username");
    User u = userService.findByUserName(username);
    return Result.success(u);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User  user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params){
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if(!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd)){
            return Result.error("参数错误");
        }

        Map<String,Object>map= ThreadLocalUtil.get();
        String username = (String)map.get("username");
        User loginUser = userService.findByUserName(username);

        if(!loginUser.getPassword().equals(DigestUtils.md5DigestAsHex(oldPwd.getBytes()))){
            return Result.error("源密码错误");
        }

        if(!newPwd.equals(rePwd)){
            return Result.error("两次密码不一致");
        }

        userService.updatePwd(newPwd);
        return Result.success();

    }

}
