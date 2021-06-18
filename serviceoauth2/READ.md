访问地址

第一步 获取授权码

http://localhost:9969/oauth/authorize?response_type=code&client_id=client&client_secret=secret&redirect_uri=http://www.baidu.com&scope=all



第二步  根据授权码，获取token
http://localhost:9969/oauth/token?grant_type=authorization_code&client_id=client&client_secret=secret&code=bxccdj&redirect_uri=http://www.baidu.com

http://localhost:9969/oauth/token?grant_type=authorization_code&code=7ONb2Q&redirect_uri=http%3A%2F%2Flocalhost%3A5020%2Fclient%2Foauth2&client_id=client123

{
    "access_token": "1741394f-ea0e-4536-8d4e-aabbd16dd267",
    "token_type": "bearer",
    "refresh_token": "5e549231-fa45-4903-ae5d-c121f7024535",
    "expires_in": 7199,
    "scope": "all"
}

第三步 token刷新
http://localhost:9969/oauth/token?grant_type=refresh_token&client_id=client&client_secret=secret&refresh_token=1a8877a1-d9ba-4295-9b0e-1cacdc11e7fc


用户名密码授权
http://localhost:8080/oauth/token?grant_type=password&client_id=client&client_secret=secret&username=admin&password=123456
用户名密码授权不需要获取code值
{
    "access_token": "206d78db-8e23-4150-a72e-d43c11b3e38e",
    "token_type": "bearer",
    "refresh_token": "3d3c80d3-1591-40bb-bf24-2203224b6d22",
    "expires_in": 7199,
    "scope": "all read write"
}



server:
  port: 5020
spring:
  security:
    user:
      name: admin
      password: 123456
    oauth2:
      client:
        client-id: client
        client-secret: secret
        access-token-uri: http://localhost:9969/oauth/token
      resource:
        id: client-resource
        user-info-uri: user-info
      authorization:
        check-token-access: http://localhost:9969/oauth/check_token
      resourceserver:
        opaquetoken:
          client-id: client-resource
  redis:
    database: 1
    host: localhost
    port: 6379
    jedis:
      pool:
        max-active: 20
        max-idle: 10
        min-idle: 5
    timeout: 10000
    client-name: redis
    
      main:
        allow-bean-definition-overriding: true










