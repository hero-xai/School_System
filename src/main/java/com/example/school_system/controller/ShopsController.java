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
        return shopsService.getByLike(shops);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public R delete(@PathVariable int id){
        return R.toAjax(shopsService.removeById(id));
    }

    @DeleteMapping("/deleteById/{id}")
    @ResponseBody
    public R deleteByid(@PathVariable int id){
        return R.toAjax(shopsService.deleteById(id));
    }


}
