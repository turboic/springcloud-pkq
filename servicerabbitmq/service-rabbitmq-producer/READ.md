cloud:
    stream:
      binders:
        defaultRabbit:
          type: rabbit
          environment: #配置rabbimq连接环境
            spring:
              rabbitmq:
                host: 10.10.10.10
                username: zhxl
                password: zhxl
                virtual-host: /
      bindings:
        output:
          destination: Subscribe  #exchange名称，交换模式默认是topic
          content-type: application/json
        input:
          destination: Publisher
          content-type: application/json
      rabbit:
        binder:
          admin-addresses: 10.10.10.10:15672
          
          
          
          
          
          
           rabbitmq:
              host: 10.10.10.10
              port: 5762
              username: zhxl
              password: zhxl
              virtual-host: /
              connection-timeout: 30000
              
              
              
             =========================================================================
             服务治理配置
             eureka:
               instance:
                 healthCheckUrlPath: ${server.servlet.context-path}/actuator/health
                 statusPageUrlPath: ${server.servlet.context-path}/actuator/info
                 homePageUrl: ${server.servlet.context-path}/
                 healthcheck:
                   enabled: true
                 hostname: 127.0.0.1
               client:
                 service-url:
                   defaultZone: http://${spring.security.user.name}:${spring.security.user.password}@${eureka.instance.hostname}:8001/eureka/
                 enabled: true