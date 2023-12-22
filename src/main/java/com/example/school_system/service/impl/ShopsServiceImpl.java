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
}
