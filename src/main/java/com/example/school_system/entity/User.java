package com.example.school_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {
    @TableId(type = IdType.AUTO)//自增策略
    private Integer id;
    private String userUser;
    private String userPass;
    private String sex;
    private String userPhone;
    private int number;
    //权限ID
    private String uid;
}
