package com.luo.big_event.service;

import com.luo.big_event.pojo.Category;

import java.util.List;


public interface CategoryService {
    void add(Category category);

    //列表查询
    List<Category> list();

    Category findById(Integer id);

    void update(Category category);
}
