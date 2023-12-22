package com.example.school_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.school_system.entity.Shopping;
import com.example.school_system.mapper.ShoppingMapper;
import com.example.school_system.service.ShoppingService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingServiceImpl extends ServiceImpl<ShoppingMapper, Shopping> implements ShoppingService {
}
