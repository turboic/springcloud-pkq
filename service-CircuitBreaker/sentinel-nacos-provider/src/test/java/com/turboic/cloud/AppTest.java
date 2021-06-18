package com.turboic.cloud;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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
        List<String> list = new ArrayList<>();
        list.add("springcloud");
        list.add("springboot");
        list.add("nacos");
        list.add("redis");
        Vector vector = new Vector(10);
        vector.addAll(list);
        vector.insertElementAt("java",3);
        for(int i = 0 ; i<vector.size();i++){
            //System.err.println(vector.remove(list.get(i)));
        }
        System.err.println(vector.get(2));
        assertTrue( true );
    }
}
