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

    /**
     * 改变审核状态
     * @param id
     * @return
     */
    @PutMapping("/updateStatus/{id}")
    @ResponseBody
    public R getStatus(@PathVariable int id){
        return shopsService.updateStatus(id);
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
