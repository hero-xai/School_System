package com.example.school_system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.school_system.commom.HttpStatus;
import com.example.school_system.commom.R;
import com.example.school_system.entity.User;
import com.example.school_system.mapper.UserMapper;
import com.example.school_system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    public R login(HttpServletRequest request,User user){
        R userResponse = new R();

        String username=user.getUserUser();
        String password=user.getUserPass();
        String uid=user.getUid();


        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<User>();
        wrapper.eq(User::getUserUser,username);
        wrapper.eq(User::getUserPass,password);
        wrapper.eq(User::getUid,uid);
        User one = this.getOne(wrapper);

        if(one!=null){
            request.getSession().setAttribute("user",user.getUserUser());
            //成功为200
            userResponse.setCode(HttpStatus.SUCCESS);
            userResponse.setMsg("登录成功");
            userResponse.setData(one);
            return userResponse;
        }
        userResponse.setCode(HttpStatus.ERROR);
        userResponse.setMsg("用户名或密码错误");
        return userResponse;
    }

    /**
     * 查询所有用户信息
     * @return
     */
    @Override
    public R queryList() {
        R all =new R();
        List<User> users = this.list();
        all.setData(users);
        return all;
    }

    /**
     * 用户名模糊查询
     * @param user 用户名
     * @return
     */
    @Override
    public R queryByName(User user) {
        String username=user.getUserUser();
        R r=new R();
        if(StringUtils.isEmpty(username)){
            r.setCode(HttpStatus.ERROR);
            r.setMsg("请输入查询信息");
            return r;
        }
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<User>();
        wrapper.like(User::getUserUser,username);
        List<User> list = this.list(wrapper);
        r.setCode(HttpStatus.SUCCESS);
        r.setData(list);
        return r;
    }

    /***
     * 获取不同角色列表
     * @return
     */
    @Override
    public R queryByUid(String menuUid) {
        R r=new R();
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<User>();
        wrapper.eq(User::getUid,menuUid);
        List<User> users = this.list(wrapper);
        r.setData(users);
        r.setCode(HttpStatus.SUCCESS);
        return r;
    }

    @Override
    public R getMy(HttpServletRequest request) {
        R r=new R();
        Object user = request.getSession().getAttribute("user");
        String username=(String)user;

        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserUser,username);
        User one = this.getOne(wrapper);
        //System.out.print(user);
        r.setData(one);
        r.setCode(HttpStatus.SUCCESS);
        return r;
    }

    @Override
    public R logout(HttpServletRequest request) {
        R r=new R();
        request.getSession().removeAttribute("user");
        r.setCode(HttpStatus.SUCCESS);
        r.setMsg("退出登录成功");
        return r;
    }

    @Override
    public R add(User user) {
        R r=new R();
        String userUser = user.getUserUser();
        String userPass = user.getUserPass();
        String uid = user.getUid();
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserUser,userUser);
        List<User> list = this.list(wrapper);
        //用户名未重复
        if(list.size()==0){
            if(StringUtils.isEmpty(userUser)||StringUtils.isEmpty(userPass)||StringUtils.isEmpty(uid)){
                r.setCode(HttpStatus.ERROR);
                r.setMsg("用户名/密码/权限为必填项,请重新输入");
                return r;
            }

            this.save(user);
            r.setCode(HttpStatus.SUCCESS);
            r.setMsg("创建成功");
        }else {
            r.setCode(HttpStatus.CONFLICT);
            r.setMsg("用户名重复，请重新输入");
        }
        return r;
    }

}
