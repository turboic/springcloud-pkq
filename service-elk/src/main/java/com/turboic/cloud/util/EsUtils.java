package com.turboic.cloud.util;

import com.turboic.cloud.config.ElasticsearchHighLevelClient;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    /**
     * BiConsumer<BulkRequest, ActionListener<BulkResponse>> bulkConsumer =
     *                 (request, bulkListener) ->
     *                         client.getClient().bulkAsync(request, RequestOptions.DEFAULT, bulkListener);
     *
     *
     *         bulkProcessor =  BulkProcessor.builder(bulkConsumer,
     *                 new BulkProcessor.Listener() {
     *                     @Override
     *                     public void beforeBulk(long executionId, BulkRequest request) {
     *                         logger.info("序号：{} ，开始执行 {} 条数据批量操作。", executionId, request.numberOfActions());
     *                     }
     *                     @Override
     *                     public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
     *                         // 在每次执行BulkRequest后调用，通过此方法可以获取BulkResponse是否包含错误
     *                         if (response.hasFailures()) {
     *                             logger.error("Bulk {} executed with failures", executionId);
     *                         } else {
     *                             logger.info("序号：{} ，执行 {} 条数据批量操作成功，共耗费{}毫秒。", executionId, request.numberOfActions(), response.getTook().getMillis());
     *                         }
     *                     }
     *                     @Override
     *                     public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
     *                         logger.error("序号：{} 批量操作失败，总记录数：{} ，报错信息为：{}", executionId, request.numberOfActions(), failure.getMessage());
     *                     }
     *
     *                 })
     *                 //每添加n个request,执行一次bulk操作
     *                 .setBulkActions(Integer.valueOf(configVo.getBulkActions()).intValue())
     *                 //每达到一定大小的请求时，执行一次bulk操作
     *                 .setBulkSize(new ByteSizeValue(Integer.valueOf(configVo.getBulkSizeMb()).intValue(), ByteSizeUnit.MB))
     *                 //每n秒执行一次bulk操作
     *                 .setFlushInterval(TimeValue.timeValueSeconds(Integer.valueOf(configVo.getFlushIntervalSeconds()).intValue()))
     *                //设置并发请求数，默认是1，表示允许执行1个并发请求，积累bulk requests和发送bulk是异步的
     *                 .setConcurrentRequests(Integer.valueOf(configVo.getConcurrentRes()).intValue())
     * //                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
     *                 .build();
     *
     *
     *     }
     */

    public void bulkAsync (String index, Map<String,Object> map) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        IndexRequest request = new IndexRequest();

        request.index(index).source(map);

        bulkRequest.add(request);

        elasticsearchHighLevelClient.getObject().bulk(bulkRequest, RequestOptions.DEFAULT);

        /*elasticsearchHighLevelClient.getObject().bulkAsync(bulkRequest, RequestOptions.DEFAULT, new ActionListener<BulkResponse>() {
            @Override
            public void onResponse(BulkResponse bulkItemResponses) {
                logger.error("bulkAsync Elasticsearch Data Success = {}" ,FastJsonUtils.objectToJson(bulkItemResponses));
            }

            @Override
            public void onFailure(Exception e) {
                logger.error("bulkAsync Elasticsearch Data Exception = {}" ,e);
            }
        });*/
    }

    public CreateIndexResponse create(String indexName) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        createIndexRequest.settings(Settings.builder()
                .put("index.number_of_shards", 5)
                .put("index.number_of_replicas", 2)

                /*.put("index.refresh_interval", "30s")
                .put("index.translog.durability", "async")
                .put("index.translog.sync_interval", "30s")
                .put("index.translog.flush_threshold_size", "1gb")
                .put("index.merge.scheduler.max_thread_count", 1)*/

                .build());

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("context");
                {
                    builder.field("type", "text");
                    builder.field("analyzer", "ik_max_word");
                    builder.field("search_analyzer", "ik_smart");
                }
                builder.endObject();
            }
            {
                builder.startObject("name");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();
            }
            {
                builder.startObject("leader");
                {
                    builder.field("type", "boolean");
                }
                builder.endObject();
            }
            {
                builder.startObject("doc");
                {
                    builder.field("type", "boolean");
                }
                builder.endObject();
            }
            {
                builder.startObject("number");
                {
                    builder.field("type", "long");
                }
                builder.endObject();
            }
            {
                builder.startObject("createTime");
                {
                    builder.field("type", "date");
                }
                builder.endObject();
            }
            {
                builder.startObject("longTime");
                {
                    builder.field("type", "long");
                }
                builder.endObject();
            }
            {
                builder.startObject("bigDecimal");
                {
                    builder.field("type", "long");
                }
                builder.endObject();
            }
            {
                builder.startObject("info");
                {
                    builder.field("type", "text");
                    builder.field("analyzer", "ik_smart");
                }
                builder.endObject();
            }
            {
                builder.startObject("id");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
            }
            {
                builder.startObject("image");
                {
                    builder.field("type", "binary");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();

        createIndexRequest.mapping(builder);

        createIndexRequest.alias(new Alias("liebe_alias"));
        createIndexRequest.setMasterTimeout(TimeValue.timeValueMinutes(2));
        createIndexRequest.setTimeout(TimeValue.timeValueSeconds(3));
        createIndexRequest.waitForActiveShards(ActiveShardCount.DEFAULT);
        try {
            CreateIndexResponse createIndexResponse = elasticsearchHighLevelClient.getObject().indices().
                    create(createIndexRequest, RequestOptions.DEFAULT);
            return createIndexResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     *
     * @param indexName
     * @param source
     * @return
     * @throws IOException
     */
    public IndexResponse index(String indexName, String source) throws IOException {
        IndexRequest indexRequest = new IndexRequest(indexName).source(source, XContentType.JSON);
        indexRequest.setRequireAlias(false);
        indexRequest.timeout(TimeValue.timeValueSeconds(3));
        indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);

        /**
         * ElasticsearchStatusException[Elasticsearch exception
         * [type=invalid_type_name_exception,
         * reason=mapping type name [_create] can't start with '_' unless it is called [_doc]]
         * request  opType(DocWriteRequest.OpType.CREATE);
         * or
         * request  setCreate(true);
         * ]
         */
        try {
            IndexResponse indexResponse = elasticsearchHighLevelClient.getObject().index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("index方法异常:{}", e);
            throw e;
        }
        return null;
    }


    public SearchResponse search(String indexName,String searchField,String searchValue) throws IOException {
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("context", "作者");
        boolBuilder.must(matchQueryBuilder);
        sourceBuilder.query(boolBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(100);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        sourceBuilder.fetchSource(new String[] {"createTime","image","name"}, new String[] {});
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(sourceBuilder);
        SearchResponse response = elasticsearchHighLevelClient.getObject().search(searchRequest, RequestOptions.DEFAULT);
        return response;
    }


    public DeleteResponse delete(String indexName, String id) {

        DeleteRequest deleteRequest = new DeleteRequest();

        deleteRequest.index(indexName);
        deleteRequest.id(id);


        deleteRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        DeleteResponse cancellable = null;
        try {
            cancellable = elasticsearchHighLevelClient.getObject().delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Cancellable cancellable = elasticsearchHighLevelClient.getObject().deleteAsync(deleteRequest, RequestOptions.DEFAULT,
                new ActionListener() {
                    @Override
                    public void onResponse(Object o) {
                        logger.info("索引删除成功={}", FastJsonUtils.objectToJson(o));
                    }

                    @Override
                    public void onFailure(Exception e) {
                        logger.info("索引删除失败={}", FastJsonUtils.objectToJson(e));
                    }
                });*/

        return cancellable;
    }

}
