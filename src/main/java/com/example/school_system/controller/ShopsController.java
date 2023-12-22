package com.example.school_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.school_system.commom.HttpStatus;
import com.example.school_system.commom.R;
import com.example.school_system.entity.Shops;
import com.example.school_system.entity.User;
import com.example.school_system.service.ShopsService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Shops")
public class ShopsController {

    @Autowired
    private ShopsService shopsService;

    @GetMapping("/getAll")
    @ResponseBody
    public R getAll(){
        return shopsService.queryShopsList();
    }

    @GetMapping("/getById/{id}")
    @ResponseBody
    public R getById(@PathVariable int id){
        return new R(HttpStatus.SUCCESS,null,shopsService.getById(id));
    }

    /***
     * 获取不同物品列表
     * @return
     */
    @GetMapping("/getSCategory/{sCategory}")
    @ResponseBody
    public R getSCategory(@PathVariable String sCategory){
        return shopsService.queryBySCategory(sCategory);
    }

    /***
     * 获取衣物
     * @return
     */
    @GetMapping("/getOne")
    @ResponseBody
    public R getOne(){
        R r=new R();
        LambdaQueryWrapper<Shops> wrapper=new LambdaQueryWrapper();
        wrapper.eq(Shops::getSCategory,"衣物");
        List shopsList = shopsService.list(wrapper);
        r.setData(shopsList);
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
        LambdaQueryWrapper<Shops> wrapper=new LambdaQueryWrapper();
        wrapper.eq(Shops::getSCategory,"文具");
        List shopsList = shopsService.list(wrapper);
        r.setData(shopsList);
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
        LambdaQueryWrapper<Shops> wrapper=new LambdaQueryWrapper();
        wrapper.eq(Shops::getSCategory,"书籍");
        List shopsList = shopsService.list(wrapper);
        r.setData(shopsList);
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
        LambdaQueryWrapper<Shops> wrapper=new LambdaQueryWrapper();
        wrapper.eq(Shops::getSCategory,"日用品");
        List shopsList = shopsService.list(wrapper);
        r.setData(shopsList);
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
        LambdaQueryWrapper<Shops> wrapper=new LambdaQueryWrapper();
        wrapper.eq(Shops::getSCategory,"杂物");
        List shopsList = shopsService.list(wrapper);
        r.setData(shopsList);
        r.setCode(1);
        return r;
    }

    @PutMapping("/getStatus/{id}")
    @ResponseBody
    public R getStatus(@PathVariable int id){
        R r=new R();
        LambdaQueryWrapper<Shops> wrapper=new LambdaQueryWrapper();
        wrapper.eq(Shops::getId,id);
        Shops one = shopsService.getOne(wrapper);

        if(one.getAutoStatus()==0){
            one.setAutoStatus(1);
        } else if (one.getAutoStatus()==1) {
            one.setAutoStatus(0);
        }
        boolean b = shopsService.saveOrUpdate(one);
        r.setCode(1);
        r.setData(one);
        r.setMsg("状态已改变");
        return r;
    }


    /***
     * 按名称信息查询
     * @param
     * @return
     */
    @PostMapping("/getByLike")
    @ResponseBody
    public R getByLike(@RequestBody Shops shops){
        String shopsName = shops.getShopsName();
        String shopsDesc = shops.getShopsDesc();
        R r=new R();
        System.out.print(shopsName+"----"+shopsDesc);
        LambdaQueryWrapper<Shops> wrapper=new LambdaQueryWrapper();
        wrapper.like(Shops::getShopsName,shopsName);
        wrapper.like(Shops::getShopsDesc,shopsDesc);
        List list = shopsService.list(wrapper);
        if(list!=null){
            r.setCode(1);
            r.setData(list);
        }
        return r;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public R delete(@PathVariable int id){
        R r=new R();
        Shops one = shopsService.getById(id);
        boolean remove = shopsService.removeById(id);
        r.setCode(1);
        r.setData(one);
        r.setMsg("菜鸡");
        return r;
    }

}
