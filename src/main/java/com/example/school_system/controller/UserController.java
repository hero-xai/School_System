
package com.example.school_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.school_system.commom.HttpStatus;
import com.example.school_system.commom.R;
import com.example.school_system.entity.User;
import com.example.school_system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public R login(HttpServletRequest request,@RequestBody User user){
        return userService.login(request,user);
    }

    /***
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public R add(@RequestBody User user){
        return userService.add(user);
    }

    /**
     * 查询所有用户
     * @return
     */
    @GetMapping("/getAll")
    @ResponseBody
    public R getAll(){
        return userService.queryList();
    }

    /***
     * 按用户名模糊查询
     * @param
     * @return
     */
    @PostMapping("/getByUser")
    @ResponseBody
    public R getByUser(@RequestBody User user){
        return userService.queryByName(user);
    }

    /**
     *通过id获取信息
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    @ResponseBody
    public R getById(@PathVariable int id){
       return new R(HttpStatus.SUCCESS,null,userService.getById(id));
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @PutMapping("/update")
    @ResponseBody
    public R update(@RequestBody User user){
        return R.toAjax(userService.updateById(user));
    }

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public R delete(@PathVariable int id){
       return R.toAjax(userService.removeById(id));
    }

    /***
     * 获取不同角色列表
     * @return
     */
    @GetMapping("/getUid/{menuUid}")
    @ResponseBody
    public R getUid(@PathVariable String menuUid){
        return userService.queryByUid(menuUid);
    }

    /***
     * 获取登录者信息
     * @return
     */
    @GetMapping("/getMy")
    @ResponseBody
    public R getMy(HttpServletRequest request){
       return userService.getMy(request);
    }

    @PostMapping("/logout")
    @ResponseBody
    public R logout(HttpServletRequest request){
        return userService.logout(request);
    }
}
