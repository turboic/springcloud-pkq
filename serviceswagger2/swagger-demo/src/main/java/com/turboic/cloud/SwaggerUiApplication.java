package com.turboic.cloud;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger-ui应用
 * @author liebe
 */
@SpringBootApplication
@EnableSwagger2
public class SwaggerUiApplication
{
    public static void main( String[] args )
    {
        System.out.println( "Hello swagger2 demo!" );
        new SpringApplicationBuilder(SwaggerUiApplication.class).run(args);
    }
}
