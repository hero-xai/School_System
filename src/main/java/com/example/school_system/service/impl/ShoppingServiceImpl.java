package com.example.school_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.school_system.commom.HttpStatus;
import com.example.school_system.commom.R;
import com.example.school_system.entity.Shopping;
import com.example.school_system.entity.User;
import com.example.school_system.mapper.ShoppingMapper;
import com.example.school_system.service.ShoppingService;
import com.example.school_system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingServiceImpl extends ServiceImpl<ShoppingMapper, Shopping> implements ShoppingService {
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
        int shoppingId=one.getId();

        LambdaQueryWrapper<Shopping> wrapper1=new LambdaQueryWrapper<>();
        wrapper1.eq(Shopping::getShoppingId,shoppingId);

        //获取本用户的商品列表
        List<Shopping> list = this.list(wrapper1);
        r.setData(list);
        r.setCode(HttpStatus.SUCCESS);
        return r;
    }
}
