package org.example.service.impl;
import org.example.service.DemoService;
import java.text.DateFormat;
import java.util.Date;
/**
 * @author demo
 */
public class DemoServiceImpl implements DemoService {
    @Override
    public String execute(String name) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL);
        return "晚上好:"+name+",现在是北京时间:"+dateFormat.format(new Date());
    }
}
