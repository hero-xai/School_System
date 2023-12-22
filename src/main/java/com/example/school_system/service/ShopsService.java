package com.example.school_system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.school_system.commom.R;
import com.example.school_system.entity.Shops;

public interface ShopsService extends IService<Shops> {
    R queryShopsList();

    R queryBySCategory(String sCategory);
}
