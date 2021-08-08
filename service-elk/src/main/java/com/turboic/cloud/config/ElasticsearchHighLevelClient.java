package com.turboic.cloud.config;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

/***
 * transport在高版本的Elasticsearch过期
 * @author liebe
 */

@Configuration
public class ElasticsearchHighLevelClient implements FactoryBean<RestHighLevelClient>, InitializingBean, DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String SCHEME = "http";

    private RestHighLevelClient restHighLevelClient;


    private String host = "10.10.10.5:9200,10.10.10.6:9200,10.10.10.7:9200";

    private String STAND_ALONE_HOST = "10.10.10.5:9200";


    /**
     * 控制Bean的实例化过程
     *
     * @return
     */
    @Override
    public RestHighLevelClient getObject() {
        return restHighLevelClient;
    }

    /**
     * 获取接口返回的实例的class
     *
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return RestHighLevelClient.class;
    }

    @Override
    public void destroy() {
        try {
            if (null != restHighLevelClient) {
                restHighLevelClient.close();
            }
        } catch (final Exception e) {
            logger.error("Error closing ElasticSearch client: ", e);
        }
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() {
        restHighLevelClient = restHighLevelClient();
    }

    private RestHighLevelClient restHighLevelClient() {
        try {
            String[] hosts = STAND_ALONE_HOST.split(",");
            List<HttpHost> httpHosts = new ArrayList<>(hosts.length);
            for (String host : hosts) {
                HttpHost h = new HttpHost(
                        host.split(":")[0],
                        Integer.parseInt(host.split(":")[1]),
                        SCHEME);
                httpHosts.add(h);
            }

            RestClientBuilder restClientBuilder = RestClient.builder(httpHosts.toArray(new HttpHost[httpHosts.size()]));
            restClientBuilder.setCompressionEnabled(true);
            restClientBuilder.setStrictDeprecationMode(true);
            restClientBuilder.setFailureListener(new RestClient.FailureListener());
            restClientBuilder.setRequestConfigCallback(builder -> builder.setConnectTimeout(5000)
                    .setSocketTimeout(5000)
                    .setSocketTimeout(5000)
                    .setAuthenticationEnabled(false)
                    .setConnectionRequestTimeout(6000));
            restHighLevelClient = new RestHighLevelClient(RestClient.builder(httpHosts.toArray(new HttpHost[0])));
        } catch (Exception e) {
            logger.error("初始化Elasticsearch Client 失败 = {}",e.getMessage());
        }
        return restHighLevelClient;
    }
}