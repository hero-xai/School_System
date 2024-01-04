package com.example.school_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.school_system.commom.HttpStatus;
import com.example.school_system.commom.R;
import com.example.school_system.entity.Goods;
import com.example.school_system.entity.Mythings;
import com.example.school_system.entity.Shops;
import com.example.school_system.entity.User;
import com.example.school_system.mapper.MythingsMapper;
import com.example.school_system.service.GoodsService;
import com.example.school_system.service.MythingsService;
import com.example.school_system.service.ShopsService;
import com.example.school_system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MythingsServiceImpl extends ServiceImpl<MythingsMapper, Mythings> implements MythingsService {

    @Autowired
    private ShopsService shopsService;
    @Autowired
    private GoodsService goodsService;

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
        int tid = one.getId();


        LambdaQueryWrapper<Mythings> myWrapper=new LambdaQueryWrapper<>();
        myWrapper.eq(Mythings::getTid,tid);
        List<Mythings> list = this.list(myWrapper);
        r.setData(list);
        r.setCode(HttpStatus.SUCCESS);
        return r;
    }

    @Override
    public R add(Mythings mythings, HttpServletRequest request) {
        R r=new R();

        //获取用户
        Object user = request.getSession().getAttribute("user");
        String username=(String)user;
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserUser,username);
        User one = userService.getOne(wrapper);

        String mythingsPhone = one.getUserPhone();
        mythings.setMythingsPhone(mythingsPhone);

        int tid= one.getId();
        mythings.setTid(tid);

        //新增操作
        boolean save = this.save(mythings);
        r.setCode(HttpStatus.SUCCESS);
        r.setMsg("新增成功");

        return r;
    }

    @Override
    public R getStatus(int id) {
        R r=new R();
        LambdaQueryWrapper<Mythings> wrapper=new LambdaQueryWrapper<Mythings>();
        wrapper.eq(Mythings::getId,id);
        Mythings one = this.getOne(wrapper);

        if(one.getMythingsStatus()==0){
            one.setMythingsStatus(1);

            //将信息添加到shops表中
            Shops shops=new Shops();
            shops.setShopsName(one.getMythingsName());
            shops.setShopsPrice(one.getMythingsPrice());
            shops.setShopsPic(one.getMythingsPic());
            shops.setShopsDesc(one.getMythingsDesc());
            shops.setShopsAddress(one.getMythingsAddress());
            shops.setShopsPhone(one.getMythingsPhone());
            shops.setShopsStatus(one.getMythingsStatus());
            shops.setSCategory(one.getCategory());
            shops.setSid(one.getTid());

            //加入数据
            shopsService.save(shops);

        } else if (one.getMythingsStatus()==1) {
            one.setMythingsStatus(0);

            //删除数据
            LambdaQueryWrapper<Goods> wrapper1=new LambdaQueryWrapper<>();
            wrapper1.eq(Goods::getGoodsName,one.getMythingsName());
            wrapper1.eq(Goods::getGoodsPrice,one.getMythingsPrice());
            wrapper1.eq(Goods::getGoodsPic,one.getMythingsPic());
            goodsService.remove(wrapper1);
        }
        boolean b = this.saveOrUpdate(one);
        r.setCode(HttpStatus.SUCCESS);
        r.setData(one);
        r.setMsg("状态已改变");
        return r;
    }
}
