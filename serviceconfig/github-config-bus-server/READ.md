配置环境
应用环境省略，rabbitmq环境省略
在github代码库上设置配置文件，针对此代码库设置
Webhooks / Add webhook

设置webhook，使用https://ngrok.com/注册登录，注册成功后跳转到dashboard页面，简单提示使用帮助命令，此处下载window版本
第一步，解压
unzip /path/to/ngrok.zip

第二步，连接账号
./ngrok authtoken 1cDmCuLaQDUepK3IqmPQqSUjko8_2qh6Y4kGGgw69kiPwbUfA

第三步，监听端口
./ngrok http 80

第四步，配置webhook的本地映射地址


客户端使用postman测试地址
localhost:8076/config-bus-client/bus
观察输出内容


-----------------------手动---------------------------------
主动修改github代码库配置文件
调用刷新地址
http://localhost:8075/actuator/bus-refresh

再使用postman测试客户端的Controller，进行值的获取，发现没有更新
重启了下github-config-bus-client获取值正常，是更新的值
-----------------------手动---------------------------------


-----------------------自动---------------------------------
配置webhhook
config server端添加spring-cloud-config-monitor



https://dashboard.ngrok.com/get-started/setup
启动 ngrox http 8075


