单独的模块，熔断和服务降级处理
![](feign-invoke-provider-service.png)

访问地址①
http://localhost:8393/hystrix


出现Hystrix Dashboard页面

小熊猫输入
在访问地址①输入
http://localhost:8393/actuator/hystrix.stream
监控单体熔断机制的微服务

在访问地址①输入
http://localhost:8393/turbine.stream
监控集群的熔断机制的微服务