package com.turboic.cloud;

import com.turboic.cloud.pojo.Clue;
import com.turboic.cloud.util.FastJsonUtils;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.Assert;

import java.io.*;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Unit test for simple App.
 */
public class SpringTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        String name = "liebe&Bean";
        String str = "程序测试";
        Assert.notNull(str,"传入的字符串是空值");
        //String beanName = "com.beijing.app.Spring.&bean";
        Map<String, String> transformedBeanNameCache = new ConcurrentHashMap<>();
        transformedBeanNameCache.computeIfAbsent(name, beanName -> {
            do {
                beanName = beanName.substring(BeanFactory.FACTORY_BEAN_PREFIX.length());
            }
            while (beanName.startsWith(BeanFactory.FACTORY_BEAN_PREFIX));
            return beanName;
        });
        System.err.println(String.format("%s爱%s","我","你"));
        File file = new File("D:\\tools");
        String  b = slashify(file.getAbsolutePath(),file.isDirectory());
        System.err.println(b);

    }

    private String slashify(String path ,boolean isDirectory){
        String p = path;
        if (File.separatorChar != '/')
            p = p.replace(File.separatorChar, '/');
        if (!p.startsWith("/"))
            p = "/" + p;
        if (!p.endsWith("/") && isDirectory)
            p = p + "/";
        return p;
    }
}
