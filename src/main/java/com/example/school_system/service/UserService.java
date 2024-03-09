package com.example.school_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.school_system.commom.R;
import com.example.school_system.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {

    R login(HttpServletRequest request, User user);

    R queryList();

    //用户名模糊查询
    R queryByName(User user);

    R queryByUid(String menuUid);


    R getMy(HttpServletRequest request);

    R logout(HttpServletRequest request);

    R add(User user);

    R deleteById(int id);

    R updateToId(User user);
}
