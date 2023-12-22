package com.example.school_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.school_system.commom.R;
import com.example.school_system.entity.History;
import com.example.school_system.entity.Shopping;
import com.example.school_system.entity.User;
import com.example.school_system.service.HistoryService;
import com.example.school_system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/History")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserService userService;

    /***
     * 新增
     * @param history
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public R add(@RequestBody History history){
        R r=new R();

        boolean save = historyService.save(history);

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

        //获取本用户id
        int hid = one.getId();


        LambdaQueryWrapper<History> myWrapper=new LambdaQueryWrapper<>();
        myWrapper.eq(History::getHid,hid);
        List<History> list = historyService.list(myWrapper);
        r.setData(list);
        r.setCode(1);
        return r;
    }
}
