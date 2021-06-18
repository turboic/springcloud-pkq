package com.turboic.cloud;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
        List<String> collection = new ArrayList<>();
        collection.add("springboot");
        collection.add("springcloud");
        String commaDelimitedString = StringUtils.collectionToCommaDelimitedString(collection);
        System.err.println(commaDelimitedString);
        Map map = new HashMap();
        System.err.println(map.size());

        //00001
        //左移动4位
        //10000 = 2*2*2*2

        int DEFAULT_INITIAL_CAPACITY = 1 << 4;
        System.err.println(DEFAULT_INITIAL_CAPACITY);
    }
}
