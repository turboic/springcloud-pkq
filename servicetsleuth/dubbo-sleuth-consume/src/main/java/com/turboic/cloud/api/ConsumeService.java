package com.turboic.cloud.api;

/**
 * @author liebe
 */
public interface ConsumeService {
    /***
     *
     * @param consume
     * @return
     */
    String consume(String consume);


    /***
     * 补充替代熔断机制
     * @param provider
     * @return
     */
    String fallbackMethodError(String provider);


    String insert(String name) ;

    String selectById(String id);
}
