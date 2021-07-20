package com.turboic.cloud.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liebe
 */
public class JavaParent {
    private static final Logger logger = LoggerFactory.getLogger(JavaParent.class);

    protected String name;

    public JavaParent(){
        logger.info("JavaParent构造方法初始化");
    }

    public JavaParent(String name) {
        this.name = name;
    }
}
