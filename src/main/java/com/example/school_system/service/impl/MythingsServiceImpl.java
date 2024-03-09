package com.example.school_system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.school_system.commom.HttpStatus;
import com.example.school_system.commom.R;
import com.example.school_system.entity.*;
import com.example.school_system.mapper.MythingsMapper;
import com.example.school_system.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MythingsServiceImpl extends ServiceImpl<MythingsMapper, Mythings> implements MythingsService {

    @Autowired
    private ShopsService shopsService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingService shoppingService;
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
    @Transactional
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


        //商品名、价格及种类非空判断
        if(StringUtils.isEmpty(mythings.getMythingsName())||StringUtils.isEmpty(mythings.getMythingsPrice())||StringUtils.isEmpty(mythings.getCategory())){
            r.setCode(HttpStatus.ERROR);
            r.setMsg("商品名、价格及种类为必填项");
            return r;
        }

        int tid= one.getId();
        mythings.setTid(tid);

        //新增操作  进入myThings表
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
            shops.setId(one.getId());
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

            boolean b = this.saveOrUpdate(one);
            r.setCode(HttpStatus.SUCCESS);
            r.setData(one);
            r.setMsg("上架成功");

            return r;

        } else if (one.getMythingsStatus()==1) {
            //已上架，查询是否通过审核（在goods中是否存在auto_status为一的商品）

            LambdaQueryWrapper<Goods> goodsWrapper=new LambdaQueryWrapper<>();
            goodsWrapper.eq(Goods::getId,one.getId());
            //goodsWrapper.eq(Goods::getAutoStatus,1);
            Goods goods = goodsService.getOne(goodsWrapper);
            if(goods==null){
                //未审核完
                r.setCode(HttpStatus.ERROR);
                r.setMsg("下架失败，商品未审核完");
                return r;
            }else{
                //审核完

                //删除数据
                r.setCode(HttpStatus.CONFLICT);
                r.setData(goods);
                r.setMsg("下架成功后再次上架需重新审核，是否继续？");
                return r;
            }
        }
        r.setCode(HttpStatus.ERROR);
        r.setMsg("状态改变出现问题");
        return r;
    }

    @Override
    @Transactional
    public R reGetStatus(Shopping shopping) {
        R r=new R();

        //删除商品列表
        LambdaQueryWrapper<Goods> deleteWrapper=new LambdaQueryWrapper();
        deleteWrapper.eq(Goods::getId,shopping.getId());
        goodsService.remove(deleteWrapper);

        //改变状态
        LambdaUpdateWrapper<Mythings> mythingsLambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        mythingsLambdaUpdateWrapper.set(Mythings::getMythingsStatus,0);
        mythingsLambdaUpdateWrapper.eq(Mythings::getId,shopping.getId());
        this.update(mythingsLambdaUpdateWrapper);

        r.setData(HttpStatus.SUCCESS);
        r.setMsg("下架成功");
        return r;
    }

    @Override
    public R removeByIdAndGoods(int id) {
        R r=new R();

        //删除mythings表
        this.removeById(id);

        //删除goods表
        if(goodsService.getById(id)!=null) {
            goodsService.removeById(id);
        }

        r.setCode(HttpStatus.SUCCESS);
        r.setMsg("删除成功");
        return r;
    }

    @Override
    public R updateStatus(int id) {

        R r=new R();

        LambdaUpdateWrapper<Mythings> wrapper=new LambdaUpdateWrapper<>();
        wrapper.set(Mythings::getMythingsStatus,0);
        wrapper.eq(Mythings::getId,id);
        this.update(wrapper);

        r.setMsg("成功");
        r.setCode(HttpStatus.SUCCESS);
        return r;
    }
}
