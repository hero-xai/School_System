package com.example.school_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.school_system.commom.R;
import com.example.school_system.entity.Goods;
import jakarta.servlet.http.HttpServletRequest;

public interface GoodsService extends IService<Goods> {
    R queryByGCategory(String gCategory);

    R getMyAudit(HttpServletRequest request);

    R add(Goods goods, HttpServletRequest request);

    R getAll();

    R getByLike(Goods goods);

    R getUp(Goods goods);

    R getDown(Goods goods);

    R addById(Goods goods,HttpServletRequest request);
}
