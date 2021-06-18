package com.turboic.cloud;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * cometed for server
 * @author liebe
 *
 * 备注：restemplate，应该在provider服务启动注册成功之后，
 * 再启动
 */
@SpringBootApplication
@EnableSwagger2
@ServletComponentScan("com.turboic.cloud.servlet")
public class CometExampleApplication
{
    private static final Log log = LogFactory.getLog(CometExampleApplication.class);
    /***
     * restemplate for rest
     * @param args
     */
    public static void main( String[] args )
    {
        log.info( "message cometed push application 服务启动中!" );
        new SpringApplicationBuilder(CometExampleApplication.class).run(args);
    }
}
