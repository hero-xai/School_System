package com.example.school_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.school_system.commom.HttpStatus;
import com.example.school_system.commom.R;
import com.example.school_system.entity.Shopping;
import com.example.school_system.entity.Shops;
import com.example.school_system.entity.User;
import com.example.school_system.service.ShoppingService;
import com.example.school_system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Shopping")
public class ShoppingController {

    @Autowired
    private ShoppingService shoppingService;

    @Autowired
    private UserService userService;

    /***
     * 新增
     * @param shopping
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public R add(@RequestBody Shopping shopping,HttpServletRequest request){
        return shoppingService.add(shopping,request);
    }
    @GetMapping("/getAll")
    @ResponseBody
    public R getAll(HttpServletRequest request){
        return shoppingService.getAll(request);
    }

    @GetMapping("/getById/{id}")
    @ResponseBody
    public R getById(@PathVariable int id){
        return new R(HttpStatus.SUCCESS,null,shoppingService.getById(id));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public R delete(@PathVariable int id){
        return R.toAjax(shoppingService.removeById(id));
    }

    @DeleteMapping("/remove/{id}")
    @ResponseBody
    public R remove(@PathVariable int id){
        return shoppingService.removeAndReturnId(id);
    }
}
