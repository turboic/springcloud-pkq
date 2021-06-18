package com.turboic.cloud.pojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * @author liebe 模拟用户的信息
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "模拟用户信息",description="模型model对象类")
public class SwaggerUser implements Serializable {
    @ApiModelProperty(name="username",value="用户名",notes="用户名字段",dataType = "String",required=true,example="admin")
    private String username;
    @ApiModelProperty(name="password",value="密码",notes="密码字段",dataType = "String",required=true,example="123456")
    private String password;

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

}
