package com.turboic.cloud.api;

/**
 * @author liebe
 */
public interface ApiService {
    /***
     *
     * @param api
     * @return
     */
    String api(String api) throws RuntimeException;


    /***
     *
     * @return
     */
    String apiError(String provider);

    /***插入到数据库
     *
     * @param json
     * @return
     * @throws RuntimeException
     */
    String insert(String json) throws RuntimeException;

    String selectById(String id);

}
