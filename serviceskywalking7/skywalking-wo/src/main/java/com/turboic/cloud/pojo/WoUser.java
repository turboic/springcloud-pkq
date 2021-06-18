package com.turboic.cloud.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author liebe wo的用户信息
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "我要订外卖",description="订外卖的实体类对象")
public class WoUser implements Serializable {
    @ApiModelProperty(name="name",value="姓名",notes="姓名字段",dataType = "String",required=true,example="张先生")
    private String name;
    @ApiModelProperty(name="mobile",value="手机号",notes="手机号字段",dataType = "String",required=true,example="18301333253")
    private String mobile;
    @ApiModelProperty(name="address",value="地址信息",notes="地址字段",dataType = "String",required=true,example="北京市朝阳区")
    private String address;

    @Override
    public String toString() {
        return "WoUser{" +
                "name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                '}';
    }


    public WoUser(String name, String mobile, String address) {
        this.name = name;
        this.mobile = mobile;
        this.address = address;
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
