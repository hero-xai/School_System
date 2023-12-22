package com.example.school_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.school_system.commom.R;
import com.example.school_system.entity.Goods;
import com.example.school_system.entity.Shops;
import com.example.school_system.entity.User;
import com.example.school_system.service.GoodsService;
import jakarta.servlet.http.HttpServletRequest;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /***
     * 新增商品
     * @param goods
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public R add(@RequestBody Goods goods,HttpServletRequest request){
        R r=new R();
        String user = (String)request.getSession().getAttribute("user");
        goods.setAutoId(user);

        int gid = goods.getGid();

        boolean save = goodsService.save(goods);

        if(save){
            r.setCode(1);
            r.setMsg("提交成功");
            return r;
        }

        r.setCode(0);
        r.setMsg("失败");
      return r;
    }

    /***
     * 获取登录者信息
     * @return
     */
    @GetMapping("/getMy")
    @ResponseBody
    public R getMy(HttpServletRequest request){
        R r=new R();
        Object user = request.getSession().getAttribute("user");
        String username=(String)user;

        LambdaQueryWrapper<Goods> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Goods::getAutoId,username);
        List<Goods> list = goodsService.list(wrapper);
        //System.out.print(user);
        r.setData(list);
        r.setCode(1);
        return r;
    }

    //以状态查询商品列表，1为可见状态
    @GetMapping("/getAll")
    @ResponseBody
    public R getAll(){
        R r=new R();
        LambdaQueryWrapper<Goods> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Goods::getAutoStatus,1);
        List<Goods> list = goodsService.list(wrapper);
        r.setData(list);
        r.setCode(1);
        return r;
    }

    /***
     * 获取衣物
     * @return
     */
    @GetMapping("/getOne")
    @ResponseBody
    public R getOne(){
        R r=new R();
        LambdaQueryWrapper<Goods> wrapper=new LambdaQueryWrapper<Goods>();
        wrapper.eq(Goods::getAutoStatus,1);
        wrapper.eq(Goods::getgCategory,"衣物");
        List<Goods> list = goodsService.list(wrapper);
        r.setData(list);
        r.setCode(1);
        return r;
    }

    /***
     * 获取文具
     * @return
     */
    @GetMapping("/getTwo")
    @ResponseBody
    public R getTwo(){
        R r=new R();
        LambdaQueryWrapper<Goods> wrapper=new LambdaQueryWrapper<Goods>();
        wrapper.eq(Goods::getAutoStatus,1);
        wrapper.eq(Goods::getgCategory,"文具");
        List<Goods> list = goodsService.list(wrapper);
        r.setData(list);
        r.setCode(1);
        return r;
    }

    /***
     * 获取书籍
     * @return
     */
    @GetMapping("/getThree")
    @ResponseBody
    public R getThree(){
        R r=new R();
        LambdaQueryWrapper<Goods> wrapper=new LambdaQueryWrapper<Goods>();
        wrapper.eq(Goods::getAutoStatus,1);
        wrapper.eq(Goods::getgCategory,"书籍");
        List<Goods> list = goodsService.list(wrapper);
        r.setData(list);
        r.setCode(1);
        return r;
    }

    /***
     * 获取日用品
     * @return
     */
    @GetMapping("/getFour")
    @ResponseBody
    public R getFour(){
        R r=new R();
        LambdaQueryWrapper<Goods> wrapper=new LambdaQueryWrapper<Goods>();
        wrapper.eq(Goods::getAutoStatus,1);
        wrapper.eq(Goods::getgCategory,"日用品");
        List<Goods> list = goodsService.list(wrapper);
        r.setData(list);
        r.setCode(1);
        return r;
    }

    /***
     * 获取杂物
     * @return
     */
    @GetMapping("/getFive")
    @ResponseBody
    public R getFive(){
        R r=new R();
        LambdaQueryWrapper<Goods> wrapper=new LambdaQueryWrapper<Goods>();
        wrapper.eq(Goods::getAutoStatus,1);
        wrapper.eq(Goods::getgCategory,"杂物");
        List<Goods> list = goodsService.list(wrapper);
        r.setData(list);
        r.setCode(1);
        return r;
    }

    /***
     * 按名称信息查询
     * @param
     * @return
     */
    @PostMapping("/getByLike")
    @ResponseBody
    public R getByLike(@RequestBody Goods goods){
        String goodsName = goods.getGoodsName();
        String goodsDesc = goods.getGoodsDesc();

        R r=new R();
        //查询数据为空
        if(goodsName==""&&goodsDesc==""){
            r.setCode(0);
            r.setMsg("没有数据你查个屁");
            return r;
        }

        LambdaQueryWrapper<Goods> wrapper=new LambdaQueryWrapper<Goods>();
        //只显示过审核的商品
        wrapper.eq(Goods::getAutoStatus,1);
        wrapper.like(Goods::getGoodsName,goodsName);
        wrapper.like(Goods::getGoodsDesc,goodsDesc);
        List<Goods> list = goodsService.list(wrapper);
        if(list!=null){
            r.setCode(1);
            r.setData(list);
        }
        return r;
    }

    /***
     * 按价格信息查询（大）
     * @param
     * @return
     */
    @PostMapping("/getUp")
    @ResponseBody
    public R getUp(@RequestBody Goods goods){
        String goodsPrice = goods.getGoodsPrice();
        R r=new R();
        //查询数据为空
        if(goodsPrice==null||goodsPrice==""){
            r.setCode(0);
            r.setMsg("没有数据你查个屁");
            return r;
        }


        LambdaQueryWrapper<Goods> wrapper=new LambdaQueryWrapper<Goods>();
        //只显示过审核的商品
        wrapper.eq(Goods::getAutoStatus,1);
        wrapper.ge(Goods::getGoodsPrice,goodsPrice);
        List<Goods> list = goodsService.list(wrapper);
            r.setCode(1);
            r.setData(list);
            r.setMsg("查询成功");
        return r;
    }

    /***
     * 按价格信息查询（小）
     * @param
     * @return
     */
    @PostMapping("/getDown")
    @ResponseBody
    public R getDown(@RequestBody Goods goods){
        String goodsPrice = goods.getGoodsPrice();
        R r=new R();
        //查询数据为空
        if(goodsPrice==null||goodsPrice==""){
            r.setCode(0);
            r.setMsg("没有数据你查个屁");
            return r;
        }

        LambdaQueryWrapper<Goods> wrapper=new LambdaQueryWrapper<Goods>();
        //只显示过审核的商品
        wrapper.eq(Goods::getAutoStatus,1);
        wrapper.le(Goods::getGoodsPrice,goodsPrice);
        List<Goods> list = goodsService.list(wrapper);
        r.setCode(1);
        r.setData(list);
        r.setMsg("查询成功");
        return r;
    }

    @GetMapping("/getById/{id}")
    @ResponseBody
    public R getById(@PathVariable int id){
        R r=new R();
        LambdaQueryWrapper<Goods> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Goods::getId,id);
        Goods one = goodsService.getOne(wrapper);
        r.setCode(1);
        r.setData(one);
        r.setMsg("菜鸡");
        return r;
    }


}
