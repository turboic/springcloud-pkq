package com.turboic.cloud.pojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * @author liebe 模拟用户的信息
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "模拟用户信息",description="模型model对象类")
public class SwaggerUser implements Serializable {
    @ApiModelProperty(name="id",value="主键Id",notes="主键字段",dataType = "String",required=true,example="id")
    private String id;
    @ApiModelProperty(name="username",value="用户名",notes="用户名字段",dataType = "String",required=true,example="admin")
    private String username;
    @ApiModelProperty(name="password",value="密码",notes="密码字段",dataType = "String",required=true,example="123456")
    private String password;
    @ApiModelProperty(name="address",value="地址",notes="地址字段",dataType = "String",required=true,example="北京市朝阳区")
    private String address;
    @ApiModelProperty(name="birth",value="出生日期",notes="时间字段",dataType = "Date",required=true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date birth;
    @ApiModelProperty(name="mobile",value="手机号码",notes="手机字段",dataType = "String",required=true,example="18301223562")
    private String mobile;
    @ApiModelProperty(name="identity",value="身份证",notes="身份证字段",dataType = "String",required=true,example="110011199802221672")
    private String identity;

    public SwaggerUser(String username, String password) {
        this.username = username;
        this.password = password;
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

    @Override
    public String toString() {
        return "SwaggerUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
