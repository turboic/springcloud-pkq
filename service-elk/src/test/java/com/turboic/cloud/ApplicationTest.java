package com.turboic.cloud;

import com.turboic.cloud.doc.Liebe;
import com.turboic.cloud.util.EsUtils;
import com.turboic.cloud.util.FastJsonUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ApplicationTest {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationTest.class);

    @Autowired
    private EsUtils esUtils;

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


        String source = FastJsonUtils.objectToJson(liebe);
        logger.info("elastic index source json = {}", source);
        try {
            IndexResponse response = esUtils.index("", source);
            logger.info("IndexResponse = {}", FastJsonUtils.objectToJson(response));
        } catch (IOException e) {
            logger.info("IOException = {}", FastJsonUtils.objectToJson(e));
        }
    }
}

