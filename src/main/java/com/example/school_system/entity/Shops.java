package com.example.school_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
/***
 * 商品表实体类   所有商品  未经审核的
 * */
@Data
public class Shops {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String shopsName;
    private String shopsPrice;
    private String shopsPic;
    private String shopsDesc;
    private String shopsAddress;
    private String shopsPhone;
    private int shopsStatus;
    //审核状态
    private int autoStatus;
    private String sCategory;
    //所属人id
    private int sid;
}
