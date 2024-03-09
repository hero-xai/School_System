package com.example.school_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.school_system.commom.HttpStatus;
import com.example.school_system.commom.R;
import com.example.school_system.entity.Shops;
import com.example.school_system.entity.User;
import com.example.school_system.mapper.ShopsMapper;
import com.example.school_system.service.ShopsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopsServiceImpl extends ServiceImpl<ShopsMapper, Shops> implements ShopsService {
    @Override
    public R queryShopsList() {
        R shops =new R();
        List<Shops> users = this.list();
        shops.setData(users);
        return shops;
    }

    @Override
    public R queryBySCategory(String sCategory) {
        R r=new R();
        LambdaQueryWrapper<Shops> wrapper=new LambdaQueryWrapper<Shops>();
        wrapper.eq(Shops::getSCategory,sCategory);
        List<Shops> shops = this.list(wrapper);
        r.setData(shops);
        r.setCode(HttpStatus.SUCCESS);
        return r;
    }

    /**
     * 修改审核状态
     * @param id
     * @return
     */
    @Override
    public R updateStatus(int id) {
        R r=new R();
        LambdaQueryWrapper<Shops> wrapper=new LambdaQueryWrapper();
        wrapper.eq(Shops::getId,id);
        Shops one = this.getOne(wrapper);

        if(one.getAutoStatus()==0){
            one.setAutoStatus(1);
            this.saveOrUpdate(one);
            r.setMsg("商品审核通过");
            r.setCode(HttpStatus.SUCCESS);
            r.setData("YES");
        } else if (one.getAutoStatus()==1) {
            one.setAutoStatus(0);
            this.saveOrUpdate(one);
            r.setMsg("商品审核未通过");
            r.setCode(HttpStatus.SUCCESS);
            r.setData("NO");
        }

        return r;

    }

    /***
     * 按名称信息查询
     * @param
     * @return
     */
    @Override
    public R getByLike(Shops shops) {
        String shopsName = shops.getShopsName();
        String shopsDesc = shops.getShopsDesc();
        R r=new R();
        LambdaQueryWrapper<Shops> wrapper=new LambdaQueryWrapper();
        wrapper.like(Shops::getShopsName,shopsName);
        wrapper.like(Shops::getShopsDesc,shopsDesc);
        List list = this.list(wrapper);
        if(list!=null){
            r.setCode(HttpStatus.SUCCESS);
            r.setData(list);
        }
        return r;
    }

    @Override
    public Boolean deleteById(int id) {
        LambdaQueryWrapper<Shops> deleteWrapper=new LambdaQueryWrapper();
        deleteWrapper.eq(Shops::getId,id);
        boolean remove = this.remove(deleteWrapper);
        return  remove;
    }
}
