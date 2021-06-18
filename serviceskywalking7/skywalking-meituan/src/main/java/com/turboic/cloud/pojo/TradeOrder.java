package com.turboic.cloud.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liebe
 */
public class TradeOrder implements Serializable {
    private String id;
    private Date creatTime;
    private String orderNumber;
    private String payType;
    private int userCount;
    private String name;
    private String mobile;
    private String address;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    @Override
    public String toString() {
        return "TradeOrder{" +
                "id='" + id + '\'' +
                ", creatTime=" + creatTime +
                ", orderNumber='" + orderNumber + '\'' +
                ", payType='" + payType + '\'' +
                ", userCount=" + userCount +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
