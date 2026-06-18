package com.luo.big_event.service;

import com.luo.big_event.pojo.Result;
import com.luo.big_event.pojo.User;

public interface UserService {
    User findByUserName(String username);

    void register(String username, String password);
}
