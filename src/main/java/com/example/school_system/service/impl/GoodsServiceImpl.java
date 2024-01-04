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

    @Override
    public R add(Goods goods, HttpServletRequest request) {
        R r=new R();
        String user = (String)request.getSession().getAttribute("user");
        goods.setAutoId(user);

        int gid = goods.getGid();

        boolean save = this.save(goods);

        if(save){
            r.setCode(HttpStatus.SUCCESS);
            r.setMsg("提交成功");
            return r;
        }

        r.setCode(HttpStatus.ERROR);
        r.setMsg("失败");
        return r;
    }

    @Override
    public R getAll() {
        R r=new R();
        LambdaQueryWrapper<Goods> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Goods::getAutoStatus,1);
        List<Goods> list = this.list(wrapper);
        r.setData(list);
        r.setCode(HttpStatus.SUCCESS);
        return r;
    }

    @Override
    public R getByLike(Goods goods) {
        String goodsName = goods.getGoodsName();
        String goodsDesc = goods.getGoodsDesc();

        R r=new R();
        //查询数据为空
        if(goodsName==""&&goodsDesc==""){
            r.setCode(HttpStatus.SUCCESS);
            r.setMsg("请输入查询信息");
            return r;
        }

        LambdaQueryWrapper<Goods> wrapper=new LambdaQueryWrapper<Goods>();
        //只显示过审核的商品
        wrapper.eq(Goods::getAutoStatus,1);
        wrapper.like(Goods::getGoodsName,goodsName);
        wrapper.like(Goods::getGoodsDesc,goodsDesc);
        List<Goods> list = this.list(wrapper);
        if(list!=null){
            r.setCode(HttpStatus.SUCCESS);
            r.setData(list);
        }
        return r;
    }

    @Override
    public R getUp(Goods goods) {
        String goodsPrice = goods.getGoodsPrice();
        R r=new R();
        //查询数据为空
        if(goodsPrice==null||goodsPrice==""){
            r.setCode(HttpStatus.SUCCESS);
            r.setMsg("请输入查询数据");
            return r;
        }


        LambdaQueryWrapper<Goods> wrapper=new LambdaQueryWrapper<Goods>();
        //只显示过审核的商品
        wrapper.eq(Goods::getAutoStatus,1);
        wrapper.ge(Goods::getGoodsPrice,goodsPrice);
        List<Goods> list = this.list(wrapper);
        r.setCode(HttpStatus.SUCCESS);
        r.setData(list);
        r.setMsg("查询成功");
        return r;
    }

    @Override
    public R getDown(Goods goods) {
        String goodsPrice = goods.getGoodsPrice();
        R r=new R();
        //查询数据为空
        if(goodsPrice==null||goodsPrice==""){
            r.setCode(HttpStatus.SUCCESS);
            r.setMsg("请输入查询数据");
            return r;
        }

        LambdaQueryWrapper<Goods> wrapper=new LambdaQueryWrapper<Goods>();
        //只显示过审核的商品
        wrapper.eq(Goods::getAutoStatus,1);
        wrapper.le(Goods::getGoodsPrice,goodsPrice);
        List<Goods> list = this.list(wrapper);
        r.setCode(HttpStatus.SUCCESS);
        r.setData(list);
        r.setMsg("查询成功");
        return r;
    }
}
