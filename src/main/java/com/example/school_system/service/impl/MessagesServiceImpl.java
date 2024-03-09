package com.example.school_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.school_system.commom.HttpStatus;
import com.example.school_system.commom.R;
import com.example.school_system.entity.Messages;
import com.example.school_system.entity.User;
import com.example.school_system.mapper.MessagesMapper;
import com.example.school_system.service.MessagesService;
import com.example.school_system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessagesServiceImpl extends ServiceImpl<MessagesMapper, Messages> implements MessagesService {

    @Autowired
    private UserService userService;

    @Override
    public R addMsg(int sid) {
        R r=new R();
        //获取用户信息
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(User::getId,sid);
        User one = userService.getOne(wrapper);

        Messages messages=new Messages();
        messages.setContent("你有商品未通过审核，已经下架，请重新编辑上架");
        messages.setReceive(one.getUserUser());

        boolean save = this.save(messages);

        r.setCode(HttpStatus.SUCCESS);
        r.setData(save);
        r.setMsg("发送成功");

        return r;
    }

    @Override
    public R getAllNot(HttpServletRequest request) {
        R r=new R();
        String user = (String)request.getSession().getAttribute("user");

        LambdaQueryWrapper<Messages> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Messages::getReadStatus,"未读");
        wrapper.eq(Messages::getReceive,user);

        List<Messages> list = this.list(wrapper);

        r.setData(list);
        r.setCode(HttpStatus.SUCCESS);
        r.setMsg("查询成功");
        return r;

    }

    @Override
    public R markAsRead(Messages[] messages) {
        R r=new R();
        for(int i=0;i<messages.length;i++){
            Messages mess=messages[i];
            LambdaUpdateWrapper<Messages> wrapper=new LambdaUpdateWrapper<>();
            wrapper.eq(Messages::getId,mess.getId());
            wrapper.set(Messages::getReadStatus,"已读");
            this.update(wrapper);
        }
        r.setCode(HttpStatus.SUCCESS);
        r.setMsg("成功");
        return r;
    }
}
