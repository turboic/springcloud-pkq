package com.turboic.cloud.dubbo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

@DubboService
public class ProviderService implements HelloService {
    private static final Log log = LogFactory.getLog(ProviderService.class);
    @Override
    public String execute(String parameter) {
        log.info("dubbo provider 程序被调用了 ="+parameter);
        // 本端是否为提供端，这里会返回true
        boolean isProviderSide = RpcContext.getContext().isProviderSide();
        System.err.println("本端是否为提供端"+isProviderSide);
        // 获取调用方IP地址
        String clientIP = RpcContext.getContext().getRemoteHost();
        System.err.println("获取调用方IP地址"+clientIP);
        // 获取当前服务配置信息，所有配置信息都将转换为URL的参数
        String application = RpcContext.getContext().getUrl().getParameter("application");
        System.err.println("获取当前服务配置信息，所有配置信息都将转换为URL的参数"+application);
        // 注意：每发起RPC调用，上下文状态会变化
        // 此时本端变成消费端，这里会返回false
        boolean isProviderSide2 = RpcContext.getContext().isProviderSide();
        System.err.println("本端是否为提供端"+isProviderSide2);
        return "hello" +parameter;
    }
}
