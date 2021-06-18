package com.turboic.cloud.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * @author liebe
 *
 */
@TableName("liebe")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Liebe implements Serializable {
    private String id;
    private String username;
    private String password;
    private Timestamp createtime;
    private Timestamp updatetime;
    public Liebe(String id,String useraname ,String password){
        this.id = id;
        this.username = useraname;
        this.password =password;
    }

}