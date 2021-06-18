package com.turboic.cloud.util;

import com.turboic.cloud.config.ElasticsearchHighLevelClient;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ESUtils {

    private static final Logger logger = LoggerFactory.getLogger(ESUtils.class);
    private final ElasticsearchHighLevelClient elasticsearchHighLevelClient;

    public ESUtils(ElasticsearchHighLevelClient elasticsearchHighLevelClient) {
        this.elasticsearchHighLevelClient = elasticsearchHighLevelClient;
    }

    /***
     * 索引请求的接口
     * @param index
     * @param id
     * @param source
     */
    public IndexResponse index(String index, String id, String source) throws IOException {
        IndexRequest indexRequest = new IndexRequest(index).id(id)
                .create(true).source(source, XContentType.JSON);
        try {
            IndexResponse indexResponse = elasticsearchHighLevelClient.getObject().index(indexRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("index方法异常:{}",e);
            throw e;
        }
        return null;
    }
}
