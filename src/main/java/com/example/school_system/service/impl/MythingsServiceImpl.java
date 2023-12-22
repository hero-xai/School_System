package com.example.school_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.school_system.entity.Mythings;
import com.example.school_system.mapper.MythingsMapper;
import com.example.school_system.service.MythingsService;
import org.springframework.stereotype.Service;

@Service
public class MythingsServiceImpl extends ServiceImpl<MythingsMapper, Mythings> implements MythingsService {
}
