package org.example;
import org.example.service.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author demo
 * 使用消费者，首先保证provider-service启动成功，正常提供服务
 */
public class ConsumerApp {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"consumer.xml"});
        context.start();
        System.in.read();
        DemoService demoService = (DemoService)context.getBean("demoService");
        String hello = demoService.execute("world");
        System.out.println( hello );
    }
}
