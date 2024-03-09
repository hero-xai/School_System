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

    @Override
    public R add(Shopping shopping, HttpServletRequest request) {
        R r=new R();

        //获取登录人id
        String user =(String) (request.getSession().getAttribute("user"));
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getUserUser,user);
        User one = userService.getOne(userWrapper);

        shopping.setAuthorId(one.getId());

        boolean save = this.save(shopping);


        r.setCode(HttpStatus.SUCCESS);
        r.setData(save);
        r.setMsg("添加成功");

        return r;
    }

    @Override
    public R removeAndReturnId(int id) {
        R r=new R();

        //获取将要删除的数据
        LambdaQueryWrapper<Shopping> getWrapper=new LambdaQueryWrapper<>();
        getWrapper.eq(Shopping::getId,id);
        Shopping shopping = this.getOne(getWrapper);
        r.setData(shopping);

        this.removeById(id);

        r.setCode(HttpStatus.SUCCESS);
        r.setMsg("删除成功");
        return r;
    }
}
