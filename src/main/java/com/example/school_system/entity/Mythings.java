package com.example.school_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class Mythings {
    @TableId(type = IdType.AUTO)
    private int id;
    private String mythingsName;
    private String mythingsPrice;
    private String mythingsPic;
    private String mythingsDesc;
    private String mythingsAddress;
    private String mythingsPhone;
    private int mythingsStatus;

    private String Category;

    private int tid;

    public Mythings() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTid() {
        return tid;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getMythingsName() {
        return mythingsName;
    }

    public void setMythingsName(String mythingsName) {
        this.mythingsName = mythingsName;
    }

    public String getMythingsPrice() {
        return mythingsPrice;
    }

    public void setMythingsPrice(String mythingsPrice) {
        this.mythingsPrice = mythingsPrice;
    }

    public String getMythingsPic() {
        return mythingsPic;
    }

    public void setMythingsPic(String mythingsPic) {
        this.mythingsPic = mythingsPic;
    }

    public String getMythingsDesc() {
        return mythingsDesc;
    }

    public void setMythingsDesc(String mythingsDesc) {
        this.mythingsDesc = mythingsDesc;
    }

    public String getMythingsAddress() {
        return mythingsAddress;
    }

    public void setMythingsAddress(String mythingsAddress) {
        this.mythingsAddress = mythingsAddress;
    }

    public String getMythingsPhone() {
        return mythingsPhone;
    }

    public void setMythingsPhone(String mythingsPhone) {
        this.mythingsPhone = mythingsPhone;
    }

    public int getMythingsStatus() {
        return mythingsStatus;
    }

    public void setMythingsStatus(int mythingsStatus) {
        this.mythingsStatus = mythingsStatus;
    }
}
