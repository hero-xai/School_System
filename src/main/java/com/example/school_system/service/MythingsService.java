package com.example.school_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.school_system.commom.R;
import com.example.school_system.entity.Mythings;
import com.example.school_system.entity.Shopping;
import jakarta.servlet.http.HttpServletRequest;

public interface MythingsService extends IService<Mythings> {
    R getAll(HttpServletRequest request);

    R add(Mythings mythings, HttpServletRequest request);

    R getStatus(int id);

    R reGetStatus(Shopping shopping);

    R removeByIdAndGoods(int id);

    R updateStatus(int id);
}
