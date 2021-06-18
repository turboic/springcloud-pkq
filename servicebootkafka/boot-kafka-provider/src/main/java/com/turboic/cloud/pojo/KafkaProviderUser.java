package com.turboic.cloud.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 * @author liebe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaProviderUser implements Serializable {
    private String name;
    private String password;
}
