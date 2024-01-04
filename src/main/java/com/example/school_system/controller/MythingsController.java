package com.example.school_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.school_system.commom.HttpStatus;
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
        return mythingsService.getAll(request);
    }
    @PostMapping("/add")
    @ResponseBody
    public R add(@RequestBody Mythings mythings,HttpServletRequest request){
        return mythingsService.add(mythings,request);
    }
    @GetMapping("/getById/{id}")
    @ResponseBody
    public R getById(@PathVariable int id){
        return new R(HttpStatus.SUCCESS,null,mythingsService.getById(id));
    }
    @PutMapping("/update")
    @ResponseBody
    public R update(@RequestBody Mythings mythings){
        return R.toAjax(mythingsService.updateById(mythings));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public R delete(@PathVariable int id){
        return R.toAjax(mythingsService.removeById(id));
    }

    @PutMapping("/getStatus/{id}")
    @ResponseBody
    public R getStatus(@PathVariable int id){
        return mythingsService.getStatus(id);
    }

}
