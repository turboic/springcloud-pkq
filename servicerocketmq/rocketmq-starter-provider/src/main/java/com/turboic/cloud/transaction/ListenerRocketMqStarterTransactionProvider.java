package com.turboic.cloud.transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liebe
 * 根据模板名称进行监听rocketMQTemplate
 */
@Slf4j
@Component
@RocketMQTransactionListener(rocketMQTemplateBeanName = "rocketMQTemplate")
public class ListenerRocketMqStarterTransactionProvider implements RocketMQLocalTransactionListener {
    private ConcurrentHashMap<String, Object> localTrans = new ConcurrentHashMap<>();
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            log.info("开始执行业务逻辑.........");
            localTrans.put(message.getHeaders().getId()+"", message.getPayload());
            log.info("完成执行业务逻辑.........");
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.error("执行本地业务逻辑出现异常，执行事务的回滚操作", e.getMessage());
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        log.info("checkLocalTransaction事务状态");
        return RocketMQLocalTransactionState.UNKNOWN;
    }
}