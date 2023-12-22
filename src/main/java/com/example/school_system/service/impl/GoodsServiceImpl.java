package com.example.school_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.school_system.commom.HttpStatus;
import com.example.school_system.commom.R;
import com.example.school_system.entity.Goods;
import com.example.school_system.entity.Shops;
import com.example.school_system.mapper.GoodsMapper;
import com.example.school_system.service.GoodsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Override
    public R queryByGCategory(String gCategory) {
        R r=new R();
        LambdaQueryWrapper<Goods> wrapper=new LambdaQueryWrapper<Goods>();
        wrapper.eq(Goods::getgCategory,gCategory);
        List<Goods> goods = this.list(wrapper);
        r.setData(goods);
        r.setCode(HttpStatus.SUCCESS);
        return r;
    }

    @Override
    public R getMyAudit(HttpServletRequest request) {
        R r=new R();
        Object user = request.getSession().getAttribute("user");
        String username=(String)user;

        LambdaQueryWrapper<Goods> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Goods::getAutoId,username);
        List<Goods> list = this.list(wrapper);
        r.setData(list);
        r.setCode(HttpStatus.SUCCESS);
        return r;
    }
}
