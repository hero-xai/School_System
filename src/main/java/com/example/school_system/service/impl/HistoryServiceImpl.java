package com.example.school_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.school_system.commom.HttpStatus;
import com.example.school_system.commom.R;
import com.example.school_system.entity.History;
import com.example.school_system.entity.User;
import com.example.school_system.mapper.HistoryMapper;
import com.example.school_system.service.HistoryService;
import com.example.school_system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {

    @Autowired
    private UserService userService;
    @Override
    public R getAll(HttpServletRequest request) {
        R r=new R();
        //获取用户
        Object user = request.getSession().getAttribute("user");
        String username=(String)user;
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserUser,username);
        User one = userService.getOne(wrapper);

        //获取本用户id
        int hid = one.getId();


        LambdaQueryWrapper<History> myWrapper=new LambdaQueryWrapper<>();
        myWrapper.eq(History::getHid,hid);
        List<History> list = this.list(myWrapper);
        r.setData(list);
        r.setCode(HttpStatus.SUCCESS);
        return r;
    }
}
