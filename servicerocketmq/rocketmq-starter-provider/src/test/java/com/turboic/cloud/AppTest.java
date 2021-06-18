package com.turboic.cloud;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.boot.system.ApplicationHome;

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
        ApplicationHome applicationHome = new ApplicationHome(AppTest.class);
        System.err.println(applicationHome.getDir().getAbsolutePath());
    }
}
