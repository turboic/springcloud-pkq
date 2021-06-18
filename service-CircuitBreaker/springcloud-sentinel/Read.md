dubbo:
application:
name: dubbo-starter-provider
id: dubbo-starter-provider
architecture: dubbo-spring-boot
#dubbo协议
protocol:
name: dubbo
port: 20880
monitor:
protocol: dubbo
address: N/A
#dubbo注册
registry:
id: dubbo-starter-provider-service
address: localhost:2181
protocol: zookeeper
port: 2181
timeout: 600000
scan:
base-packages: com.turboic.cloud.service.impl