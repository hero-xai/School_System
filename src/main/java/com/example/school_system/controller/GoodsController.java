package com.example.school_system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.school_system.commom.HttpStatus;
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
        return goodsService.add(goods,request);
    }

    /***
     * 获取登陆人审核的商品信息
     * @return
     */
    @GetMapping("/getMyAudit")
    @ResponseBody
    public R getMy(HttpServletRequest request){
       return goodsService.getMyAudit(request);
    }

    //以状态查询商品列表，1为可见状态
    @GetMapping("/getAll")
    @ResponseBody
    public R getAll(){
        return goodsService.getAll();
    }

    /***
     * 获取不同物品列表
     * @return
     */
    @GetMapping("/getGCategory/{gCategory}")
    @ResponseBody
    public R getGCategory(@PathVariable String gCategory){
        return goodsService.queryByGCategory(gCategory);
    }

    /***
     * 按名称信息查询
     * @param
     * @return
     */
    @PostMapping("/getByLike")
    @ResponseBody
    public R getByLike(@RequestBody Goods goods){
        return goodsService.getByLike(goods);
    }

    /***
     * 按价格信息查询（大）
     * @param
     * @return
     */
    @PostMapping("/getUp")
    @ResponseBody
    public R getUp(@RequestBody Goods goods){
        return goodsService.getUp(goods);
    }

    /***
     * 按价格信息查询（小）
     * @param
     * @return
     */
    @PostMapping("/getDown")
    @ResponseBody
    public R getDown(@RequestBody Goods goods){
        return goodsService.getDown(goods);
    }

    @GetMapping("/getById/{id}")
    @ResponseBody
    public R getById(@PathVariable int id){
        return new R(HttpStatus.SUCCESS,null,goodsService.getById(id));
    }


}
