# springcloud-pkq
服务治理
admin-server及客户端监控代码的提交

增加feign调用等等功能

加入熔断测试

使用turbine-server监控微服务feign-consumer

加入git-server配置

加入springcloud config server及client的配置，手动刷新github配置信息

加入springcloud stream Rabbitmq

加入swagger2-ui配置demo的信息

加入rabbitmq的七种工作模式

加入zipkin-server服务的调用测试结果

加入spring-cloud-gateway限流熔断等机制

spring-boot集成dubbo最新版本

高版本maven编译代码，出现找不到程序包的问题，可能是maven的引入依赖存在问题
建议独立拆分

避免重复内容的依赖引入

拆分独立的模块更加清晰，定位扩展解决问题更顺畅。

 <properties>
   <spring-boot.repackage.skip>true</spring-boot.repackage.skip>
  </properties>
  
不是要记住问题，而是要掌握分析解决问题的能力。
