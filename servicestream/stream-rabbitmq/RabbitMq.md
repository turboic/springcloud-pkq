RabbitMQ的其中运行模式
官网地址:https://www.rabbitmq.com/getstarted.html
hello world->
Work queues
Publish/Subscribe
Routing
Topics
   不存在路由键，一系列字符串，通过圆点限制，格式如----》"stock.usd.nyse", "nyse.vmw", "quick.orange.rabbit".
   255个字节限制。
   Topic exchange
   Topic exchange is powerful and can behave like other exchanges.   
   When a queue is bound with "#" (hash) binding key - it will receive all the messages, regardless of the routing key - like in fanout exchange.
    When special characters, "*" (star) and "#" (hash), aren't used in bindings, the topic exchange will behave just like a direct one.
   Receiving messages based on a pattern (topics)
RPC
Publisher Confirms
