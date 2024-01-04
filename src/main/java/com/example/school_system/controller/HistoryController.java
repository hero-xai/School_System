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
        return R.toAjax(historyService.save(history));
    }
    @GetMapping("/getAll")
    @ResponseBody
    public R getAll(HttpServletRequest request){
        return historyService.getAll(request);
    }
}
