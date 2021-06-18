package com.turboic.cloud.swagger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * @author liebe
 * Swagger2的配置类文件
 *
 * html页面的访问地址：
 * http://localhost:9092/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerUiConfiguration {

    private final static String SWAGGER_SCAN_BASE_PACKAGE = "com.turboic.cloud.controller";

    /**
     * 此处根据注解匹配
     * @return
     *
     * 标注类上的注解对所有的方法都有作用
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    /***
     * Api信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SwaggerUi的演示工程")
                .description("熟悉演示Swagger的使用方法")
                .version("1.0")
                .termsOfServiceUrl("https://github.com")
                .license("开源协议")
                .contact(contact())
                .build();
    }

    /***
     * 联系人
     * @return
     */
    private Contact contact(){
        return new Contact("liebe","https://www.baidu.com","xxxxxxxxxx@qq.com");
    }
}