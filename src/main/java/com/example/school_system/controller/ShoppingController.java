package com.example.school_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    public R add(@RequestBody Shopping shopping){
        R r=new R();
        boolean save = shoppingService.save(shopping);

        if(save){
            r.setCode(1);
            r.setMsg("添加成功");
            return r;
        }

        r.setCode(0);
        r.setMsg("失败");
        return r;
    }
    @GetMapping("/getAll")
    @ResponseBody
    public R getAll(HttpServletRequest request){
        R r=new R();

        //获取用户
        Object user = request.getSession().getAttribute("user");
        String username=(String)user;
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserUser,username);
        User one = userService.getOne(wrapper);
        int shoppingId=one.getId();

        LambdaQueryWrapper<Shopping> wrapper1=new LambdaQueryWrapper<>();
        wrapper1.eq(Shopping::getShoppingId,shoppingId);

        //获取本用户的商品列表
        List<Shopping> list = shoppingService.list(wrapper1);
        r.setData(list);
        r.setCode(1);
        return r;
    }

    @GetMapping("/getById/{id}")
    @ResponseBody
    public R getById(@PathVariable int id){
        R r=new R();
        Shopping byId = shoppingService.getById(id);
        r.setData(byId);
        r.setCode(1);
        return r;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public R delete(@PathVariable int id){
        R r=new R();
        boolean remove = shoppingService.removeById(id);
        r.setCode(1);
        r.setData(remove);
        r.setMsg("菜鸡");
        return r;
    }

    @DeleteMapping("/remove/{id}")
    @ResponseBody
    public R remove(@PathVariable int id){
        R r=new R();
        Shopping one = shoppingService.getById(id);
        boolean remove = shoppingService.removeById(id);
        r.setCode(1);
        r.setData(one);
        r.setMsg("菜鸡");
        return r;
    }
}
