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
import java.util.Base64;
import java.util.Date;

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
}

