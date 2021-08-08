package com.turboic.cloud.doc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author liebe
 */
public class Liebe implements Serializable {
    private String name;

    private String context;

    private Integer number;

    private Date createTime;

    private BigDecimal bigDecimal;

    private Boolean isDoc;

    private Long longTime;

    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public Boolean getDoc() {
        return isDoc;
    }

    public void setDoc(Boolean doc) {
        isDoc = doc;
    }

    public Long getLongTime() {
        return longTime;
    }

    public void setLongTime(Long longTime) {
        this.longTime = longTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
