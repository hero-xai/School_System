package com.example.school_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.school_system.commom.R;
import com.example.school_system.entity.History;
import jakarta.servlet.http.HttpServletRequest;

public interface HistoryService extends IService<History> {
    R getAll(HttpServletRequest request);
}
