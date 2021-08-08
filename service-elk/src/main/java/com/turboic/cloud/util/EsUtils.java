package com.turboic.cloud.util;

import com.turboic.cloud.config.ElasticsearchHighLevelClient;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * @author liebe
 */
@Component
public class EsUtils {

    private static final Logger logger = LoggerFactory.getLogger(EsUtils.class);

    private final ElasticsearchHighLevelClient elasticsearchHighLevelClient;

    public EsUtils(ElasticsearchHighLevelClient elasticsearchHighLevelClient) {
        this.elasticsearchHighLevelClient = elasticsearchHighLevelClient;
    }


    /***
     *
     * @param indexName
     * @param source
     * @return
     * @throws IOException
     */
    public IndexResponse index(String indexName,String source) throws IOException {
        IndexRequest indexRequest = new IndexRequest(indexName).source(source, XContentType.JSON);
        indexRequest.create(true);
        indexRequest.setRequireAlias(false);
        indexRequest.timeout(TimeValue.timeValueSeconds(3));
        indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        indexRequest.opType(DocWriteRequest.OpType.CREATE);
        indexRequest.setPipeline("pipeline");
        try {
            IndexResponse indexResponse = elasticsearchHighLevelClient.getObject().index(indexRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("index方法异常:{}",e);
            throw e;
        }
        return null;
    }
}
