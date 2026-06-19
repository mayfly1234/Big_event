package com.luo.big_event.controller;

import com.luo.big_event.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @GetMapping("/list")
    public Result<String>list(){
        return Result.success("欢迎,"+"所有的文章数据...");
    }
}
