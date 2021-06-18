package com.turboic.cloud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
/**
 * @author liebe
 */
@SpringBootApplication
public class DocumentApiUiApplication
{
    private static final Logger logger = LoggerFactory.getLogger(DocumentApiUiApplication.class);
    public static void main( String[] args )
    {
        logger.info( "Hello 接口文档微服务启动成功!" );
        new SpringApplicationBuilder(DocumentApiUiApplication.class).run(args);
    }
}
