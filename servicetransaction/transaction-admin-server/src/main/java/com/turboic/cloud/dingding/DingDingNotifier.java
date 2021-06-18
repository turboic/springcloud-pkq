package com.turboic.cloud.dingding;

import com.alibaba.fastjson.JSONObject;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author liebe
 */
public class DingDingNotifier extends AbstractStatusChangeNotifier {
    private static final Logger logger = LoggerFactory.getLogger(DingDingNotifier.class);
    @Autowired
    private DingDingMessageUtil dingDingMessageUtil;

    public DingDingNotifier(InstanceRepository repository) {
        super(repository);
    }
    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        String serviceName = instance.getRegistration().getName();
        String serviceUrl = instance.getRegistration().getServiceUrl();
        String status = instance.getStatusInfo().getStatus();
        Map<String, Object> details = instance.getStatusInfo().getDetails();
        StringBuilder str = new StringBuilder();
        str.append("监控报警 : 【").append(serviceName).append("】");
        str.append("【服务地址】").append(serviceUrl);
        str.append("【状态】").append(status);
        str.append("【详情】").append(JSONObject.toJSONString(details));
        logger.info("钉钉要通知的消息内容:{}",str);
        return Mono.fromRunnable(() -> {
            logger.info("开始调用钉钉接口发送消息:{},发送实例:{}",str,dingDingMessageUtil);
            String result = dingDingMessageUtil.sendActionCardMessage(str.toString());
            //String result = dingDingMessageUtil.sendTextMessage(str.toString());
            logger.info("结束调用钉钉接口调用,返回实例:{}",result);
        });
    }
}