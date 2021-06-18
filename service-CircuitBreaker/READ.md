
![](zipkin.png)
dubbo:
  application:
    name: dubbo-starter-reference
    id: dubbo-starter-reference
  #dubbo注册
  registry:
    id: dubbo-starter-reference-service
    address: localhost:2181
    protocol: zookeeper
    port: 2181
    timeout: 600000
    scan:
      base-packages: com.turboic.cloud
      
      
      
      java -jar dubbo-admin-0.2.0-SNAPSHOT.jar --server.port=8088