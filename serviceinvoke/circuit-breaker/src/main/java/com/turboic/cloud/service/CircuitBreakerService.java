package com.turboic.cloud.service;
import com.turboic.cloud.vo.App;
/**
 * @author liebe
 */
public interface CircuitBreakerService {
    /**
     * 测试熔断超时的效果
     * @param id
     * @return
     */
    App circuitGet(String id);
}
