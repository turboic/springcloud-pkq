package org.example;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author demo
 */
public class ProviderApp {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("provider.xml");
        context.start();
        System.in.read();
    }
}
