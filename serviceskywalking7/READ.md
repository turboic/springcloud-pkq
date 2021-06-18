两种组件实现的网关
作用是一样的

consume服务调用provider
provider的服务由service和dubbo共同调用组合完成的

主要是查看链路，每个微服务都注册到eureak中去

dubbo可能需要使用zookeeper

第一步zuul网关，跳转到consume



第二步，consume到下层级别的调用


可以多个consume实现路由的功能

实现外卖，商家，骑手的功能


浏览器访问
-javaagent:D:\cloud\springcloud-pkq\serviceskywalking7\agent\skywalking-agent.jar -Dskywalking.agent.service_name=skywalking-ui-liebe -Dskywalking.collector.backend_service=localhost:11800