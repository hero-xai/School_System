package com.example.school_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Messages {

    @TableId(type = IdType.AUTO)
    private int id;

    //内容
    private String content;

    //接收人
    private String receive;

    //状态：已读未读
    private String readStatus;


}
