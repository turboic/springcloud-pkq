package com.turboic.cloud;

import com.turboic.cloud.doc.Liebe;
import com.turboic.cloud.util.EsUtils;
import com.turboic.cloud.util.FastJsonUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ApplicationTest {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationTest.class);

    @Autowired
    private EsUtils esUtils;

    @Test
    public void create() {
        String indexName = "liebe-20210808";
        logger.info("elastic index source json = {}", indexName);
        try {
            CreateIndexResponse response = esUtils.create(indexName);
            logger.info("CreateIndexResponse = {}", FastJsonUtils.objectToJson(response));
        } catch (IOException e) {
            logger.info("IOException = {}", FastJsonUtils.objectToJson(e));
        }
    }

    @Test
    public void test() {
        String indexName = "liebe-20210808";

        Liebe liebe = new Liebe();
        liebe.setBigDecimal(new BigDecimal(20210808));
        liebe.setContext("温馨提示：“你是你人生的作者， 何必把剧本写得苦不堪言”（lithromantic）回避性依赖人格 \u200B\u200B\u200B\u200B");
        liebe.setName("elastic");
        liebe.setDoc(true);
        liebe.setNumber(1);
        liebe.setLongTime(System.currentTimeMillis());
        liebe.setCreateTime(new Date());
        InputStream in;
        byte[] data = null;
        try {
            in = new FileInputStream("C:\\Users\\liebe\\Pictures\\234.jpg");
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        liebe.setImage("data:image/jpeg;base64,"+Base64.getEncoder().encodeToString(data));
        String source = FastJsonUtils.objectToJson(liebe);
        logger.info("elastic index source json = {}", liebe.getImage());
        try {
            IndexResponse response = esUtils.index(indexName, source);
            logger.info("IndexResponse = {}", FastJsonUtils.objectToJson(response));
        } catch (IOException e) {
            logger.info("IOException = {}", FastJsonUtils.objectToJson(e));
        }
    }


    @Test
    public void search() {
        String indexName = "liebe-20210808";
        try {
            String searchField = "context";
            String searchValue = "作者";
            SearchResponse response = esUtils.search(indexName,searchField,searchValue);
            SearchHits hits = response.getHits();
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                logger.info("search data -> {}",hit.getSourceAsString());
            }
            logger.info("SearchResponse = {}", FastJsonUtils.objectToJson(response));
        } catch (IOException e) {
            logger.info("IOException = {}", FastJsonUtils.objectToJson(e));
        }
    }

    @Test
    public void delete() {
        String indexName = "liebe-20210808";
        String id = "ym69JHsBC3Pl1tH0ftti";
        DeleteResponse response = esUtils.delete(indexName,id);
        logger.info("SearchResponse = {}", FastJsonUtils.objectToJson(response));
    }


    @Test
    public void bulkAsync() {
        //map.put("CHAR_TYPE",'B');
        //map.put("UPDATE_TIME",new Timestamp(System.currentTimeMillis()));


        String indexName = "liebe_2021090723";
        Map<String,Object> map = new HashMap<>();
        map.put("NAME","姓名");
        map.put("AGE",89);
        map.put("CREATE_TIME",new Date());

        map.put("BIG_DECIMAL",new BigDecimal(100));
        map.put("CREATE_TIME",new Date());
        map.put("RESOURCE_ID",202L);
        map.put("VERSION",2.0);

        map.put("PRICE",8.0d);
        map.put("IS_TRUE",true);
        map.put("IS_FALSE",false);

        map.put("BYTE_TYPE","elasticsearch.txt".getBytes(StandardCharsets.UTF_8));

        map.put("LONG_TYPE",System.currentTimeMillis());

        map.put("IP","10.10.10.22/24");
        map.put("LOCATION","41.12,12.28");

        try {
            esUtils.bulkAsync(indexName,map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

