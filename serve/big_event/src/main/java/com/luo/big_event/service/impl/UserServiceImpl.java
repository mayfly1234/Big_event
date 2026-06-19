package com.luo.big_event.service.impl;
import com.luo.big_event.mapper.UserMapper;
import com.luo.big_event.pojo.User;
import com.luo.big_event.service.UserService;
import com.luo.big_event.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
        User u= userMapper.findByUserName(username);
        return u ;
    }

    @Override
    public void register(String username, String password) {

        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        userMapper.add(username,md5Password);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update( user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl, id);
    }


}
