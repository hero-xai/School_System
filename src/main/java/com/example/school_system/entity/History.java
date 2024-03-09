package com.example.school_system.entity;

public class History {

    private int historyId;
    private String historyName;
    private String historyPrice;
    private String historyPic;
    private String historyDesc;
    private String historyAddress;
    private String historyPhone;
    private int hid;

    public History() {
    }

    public String getHistoryName() {
        return historyName;
    }

    public void setHistoryName(String historyName) {
        this.historyName = historyName;
    }

    public String getHistoryPrice() {
        return historyPrice;
    }

    public void setHistoryPrice(String historyPrice) {
        this.historyPrice = historyPrice;
    }

    public String getHistoryPic() {
        return historyPic;
    }

    public void setHistoryPic(String historyPic) {
        this.historyPic = historyPic;
    }

    public String getHistoryDesc() {
        return historyDesc;
    }

    public void setHistoryDesc(String historyDesc) {
        this.historyDesc = historyDesc;
    }

    public String getHistoryAddress() {
        return historyAddress;
    }

    public void setHistoryAddress(String historyAddress) {
        this.historyAddress = historyAddress;
    }

    public String getHistoryPhone() {
        return historyPhone;
    }

    public void setHistoryPhone(String historyPhone) {
        this.historyPhone = historyPhone;
    }

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }
}
