package com.turboic.cloud;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.builder.SpringApplicationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * zookeeper-spring-cloud 应用
 * @author liebe
 * 客户端浏览器访问地址，进行测试
 * localhost:3002/oauth/authorize?response_type=code&client_id=resourceApp&client_secret=resourceApp&redirect_uri=http://localhost:3003/login/auth&scope=all
 */
@SpringBootApplication
@EnableSwagger2
public class Oauth2AuthResourceApplication
{
    private final static Log log = LogFactory.getLog(Oauth2AuthResourceApplication.class);
    public static void main( String[] args )
    {
        System.out.println( "Hello spring cloud 集成oauth2资源客户端应用启动!" );
        log.info("Hello spring cloud 集成oauth2资源客户端应用启动!");
        new SpringApplicationBuilder(Oauth2AuthResourceApplication.class).run(args);
    }
    /**
     * java   spring   spring-security   spring-cloud   rest-security
     *
     *
     * 温馨提示：将鼠标放在语句上可以显示对应的英文。   或者   切换至中英文显示
     * 到目前为止，我读过的大多数教程都在API网关上使用@EnableOAuth2Sso而不是@EnableResourceServer 。 有什么区别？ 相比之下， OAuth2Sso作用是什么？
     *
     * 详细信息：我正在为基于Spring的微服务和单页应用程序实现安全/基础架构。 有一段时间，虽然我们没有安全要求，但是在不同的主机（CORS方）上，SPA直接与开放式微服务进行了对话。
     *
     * 现在我使用spring-oauth和spring-zuul添加一层安全性和网关模式。 所以我有一个服务（UAA服务）与@EnableAuthorizationServer并与网关@EnableZuulProxy ＆ @EnableResourceServer 。 我只需要密码授权类型，因此每个SPA都有自己的登录表单，并通过网关对uaa-service令牌端点进行身份验证，然后继续使用该令牌进行进一步的请求。
     *
     * 这种方法有什么问题吗？ 我应该使用@EnableOAuth2Sso吗？
     *
     * java spring spring-security spring-cloud rest-security
     * 1 个回复
     * 按投票数排序按时间排序
     *
     *
     * ===============>>#1 票数：63
     * 这些注释使用不同的OAuth 2.0角色标记您的服务。
     *
     * @EnableResourceServer注释意味着您的服务（就OAuth 2.0而言 - 资源服务器）需要访问令牌才能处理请求。 在调用资源服务器之前，应通过OAuth 2.0客户端从授权服务器获取访问令牌。
     *
     * @ EnableOAuth2Sso：将您的服务标记为OAuth 2.0客户端。 这意味着它将负责将资源所有者（最终用户）重定向到用户必须输入其凭据的授权服务器。 完成后，用户将被重定向回具有授权码的客户端（不要与访问代码混淆）。 然后客户端通过调用授权服务器获取授权代码并将其交换为访问令牌。 只有在此之后，客户端才能使用访问令牌调用资源服务器。
     *
     * 另外，如果您查看@EnableOAuth2Sso注释的源代码，您将看到两件有趣的事情：
     *
     * @EnableOAuth2Client 。 这是您的服务成为OAuth 2.0客户端的地方。 如果您通过OAuth2RestTemplate调用这些服务，则可以将访问令牌（在交换授权代码之后）转发到下游服务。
     * @EnableConfigurationProperties(OAuth2SsoProperties.class) 。 OAuth2SsoProperties只有一个属性String loginPath ，默认为/login 。 这将拦截OAuth2ClientAuthenticationProcessingFilter对/login的浏览器请求，并将用户重定向到授权服务器。
     * 我应该使用@ EnableOAuth2Sso吗？
     *
     * 这取决于：
     *
     * 如果您希望您的API网关是使用授权代码流或资源所有者密码凭据流与浏览器交互的OAuth 2.0客户端，那么答案是肯定的，您可能应该这样做。 我说可能是因为我不确定@EnableOAuth2Sso支持资源所有者密码凭证流程。 无论如何，我建议你使用授权代码流程，除非你真的（真的！）有充分的理由不这样做。 顺便说一下，使用授权代码流时，您可能希望将下游微服务标记为@EnableResourceServer 。 然后API网关将是OAuth 2.0客户端，您的微服务将是OAuth 2.0资源服务器，这对我来说似乎合乎逻辑。
     * 如果您不需要与浏览器交互（例如客户端凭据流 ）或者您使用了使用隐式流的 SPA，则应使用@EnableResourceServer，这意味着它将仅接受具有有效访问令牌的请求。
     */
}
