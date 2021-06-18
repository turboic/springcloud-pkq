微服务调用消费模块

调用注册到eureka-server的服务provider-service
通过服务名称可以调用spring.application.name
即使eureka-server宕机停掉，服务依然可以调用，因为拉取eureka-server服务信息缓存到本地


postman测试调用结果如下
![](provider-service.png)
