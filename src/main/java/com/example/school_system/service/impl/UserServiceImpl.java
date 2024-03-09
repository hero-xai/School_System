package com.example.school_system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.school_system.commom.HttpStatus;
import com.example.school_system.commom.R;
import com.example.school_system.commom.UserUid;
import com.example.school_system.entity.History;
import com.example.school_system.entity.Mythings;
import com.example.school_system.entity.User;
import com.example.school_system.mapper.UserMapper;
import com.example.school_system.service.GoodsService;
import com.example.school_system.service.HistoryService;
import com.example.school_system.service.MythingsService;
import com.example.school_system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
        userResponse.setMsg("用户名密码或权限错误，请重新输入");
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

    /**
     * 普通用户删除，外键关联无法删除主表
     * @param id
     * @return
     */
    @Override
    public R deleteById(int id) {
//        //todo
        R r=new R();
//
//        //判断是否为普通用户
//        User byId = this.getById(id);
//        String uid = byId.getUid();
//        if(UserUid.普通用户.name().equals(uid)){
//            //为普通用户
//            //获取该用户已上架商品
//            LambdaQueryWrapper<Mythings> myThingWrapper =new LambdaQueryWrapper<>();
//            myThingWrapper.eq(Mythings::getTid,id);
//            List<Mythings> myThingsList = mythingsService.list(myThingWrapper);
//
//            LambdaQueryWrapper<History> historyWrapper =new LambdaQueryWrapper<>();
//            historyWrapper.eq(History::getHid,id);
//            List<History> historyList = historyService.list(historyWrapper);
//
//            if(myThingsList.size()!=0){
//                r.setCode(HttpStatus.SUCCESS);
//                r.setMsg("用户货架已有商品");
//                r.setData(id);
//                return r;
//            }
//
//            if(historyList.size()!=0){
//                r.setCode(HttpStatus.SUCCESS);
//                r.setMsg("用户已购商品");
//                r.setData(id);
//                return r;
//            }
//
//
//        }

        //管理员审核员和无数据的普通人员
        LambdaQueryWrapper<User> wrapper =new LambdaQueryWrapper();
        wrapper.eq(User::getId,id);
        boolean remove = this.remove(wrapper);

        r.setCode(HttpStatus.SUCCESS);
        r.setMsg("删除成功");
        r.setData(remove);

        return r;
    }

    /***
     * 修改用户信息，用户名密码不为空
     * @param user
     * @return
     */
    @Override
    public R updateToId(User user) {
        R r=new R();

        String userUser=user.getUserUser();
        String userPass=user.getUserPass();
        if(StringUtils.isEmpty(userUser)||StringUtils.isEmpty(userPass)){
         //为空修改失败
            if(StringUtils.isEmpty(userUser)) {
                r.setCode(HttpStatus.ERROR);
                r.setMsg("用户名不可为空");
                return r;
            }
            r.setCode(HttpStatus.ERROR);
            r.setMsg("密码不可为空");
            return r;
        }

        //成功
        boolean byId = this.updateById(user);
        r.setCode(HttpStatus.SUCCESS);
        r.setMsg("修改成功");
        r.setData(byId);

        return r;
    }

}
