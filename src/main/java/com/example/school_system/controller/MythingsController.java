package com.example.school_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.school_system.commom.R;
import com.example.school_system.entity.*;
import com.example.school_system.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Mythings")
public class MythingsController {


    @Autowired
    private MythingsService mythingsService;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ShopsService shopsService;

    @GetMapping("/getAll")
    @ResponseBody
    public R getAll(HttpServletRequest request){
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
        List<Mythings> list = mythingsService.list(myWrapper);
        r.setData(list);
        r.setCode(1);
        return r;
    }
    @PostMapping("/add")
    @ResponseBody
    public R add(@RequestBody Mythings mythings,HttpServletRequest request){
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
        boolean save = mythingsService.save(mythings);
        r.setCode(1);
        r.setMsg("新增成功");

        return r;
    }
    @GetMapping("/getById/{id}")
    @ResponseBody
    public R getById(@PathVariable int id){
        R r=new R();
        Mythings byId = mythingsService.getById(id);
        r.setCode(1);
        r.setData(byId);
        r.setMsg("菜鸡");
        return r;
    }
    @PutMapping("/update")
    @ResponseBody
    public R update(@RequestBody Mythings mythings){
        R r=new R();
        boolean b = mythingsService.updateById(mythings);
        r.setMsg("修改成功");
        r.setCode(1);
        return r;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public R delete(@PathVariable int id){
        R r=new R();
        boolean remove = mythingsService.removeById(id);
        r.setCode(1);
        r.setData(remove);
        r.setMsg("菜鸡");
        return r;
    }

    @PutMapping("/getStatus/{id}")
    @ResponseBody
    public R getStatus(@PathVariable int id){
        R r=new R();
        LambdaQueryWrapper<Mythings> wrapper=new LambdaQueryWrapper<Mythings>();
        wrapper.eq(Mythings::getId,id);
        Mythings one = mythingsService.getOne(wrapper);

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
        boolean b = mythingsService.saveOrUpdate(one);
        r.setCode(1);
        r.setData(one);
        r.setMsg("状态已改变");
        return r;
    }

}
