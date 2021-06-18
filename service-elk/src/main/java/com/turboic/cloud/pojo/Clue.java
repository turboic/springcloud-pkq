package com.turboic.cloud.pojo;

import com.turboic.cloud.index.Index;

import java.util.Date;

public class Clue implements Cloneable{
    private String id;
    private Date createTime;
    private String code;
    private String content;
    private String status;
    private Date updateTime;
    private String crowd;
    private String level;
    private String sourceUnit;
    private String actWay;
    private String subject;
    private String name;
    private String address;
    private String title;
    private String username;
    private String password;
    private int age;
    private String createUnit;
    private String updateUnit;
    private Index index;

    @Override
    public Object clone()  {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCrowd() {
        return crowd;
    }

    public void setCrowd(String crowd) {
        this.crowd = crowd;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSourceUnit() {
        return sourceUnit;
    }

    public void setSourceUnit(String sourceUnit) {
        this.sourceUnit = sourceUnit;
    }

    public String getActWay() {
        return actWay;
    }

    public void setActWay(String actWay) {
        this.actWay = actWay;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCreateUnit() {
        return createUnit;
    }

    public void setCreateUnit(String createUnit) {
        this.createUnit = createUnit;
    }

    public String getUpdateUnit() {
        return updateUnit;
    }

    public void setUpdateUnit(String updateUnit) {
        this.updateUnit = updateUnit;
    }

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Clue{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", code='" + code + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", updateTime=" + updateTime +
                ", crowd='" + crowd + '\'' +
                ", level='" + level + '\'' +
                ", sourceUnit='" + sourceUnit + '\'' +
                ", actWay='" + actWay + '\'' +
                ", subject='" + subject + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", title='" + title + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", createUnit='" + createUnit + '\'' +
                ", updateUnit='" + updateUnit + '\'' +
                '}';
    }
}
