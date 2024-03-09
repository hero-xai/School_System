package com.example.school_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.school_system.commom.R;
import com.example.school_system.entity.Goods;
import com.example.school_system.entity.Shopping;
import jakarta.servlet.http.HttpServletRequest;

public interface ShoppingService extends IService<Shopping> {
    R getAll(HttpServletRequest request);

    R add(Shopping shopping, HttpServletRequest request);

    R removeAndReturnId(int id);
}
