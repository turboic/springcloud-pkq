
删除docker所有容器
docker stop $(docker ps -q) & docker rm $(docker ps -aq)

第一步，docker安装elasticsearch
   
   如果需要自己指定配置文件，那么配置文件elasticsearch一定要写对啊
   
   docker run -dti --restart unless-stopped --name elasticsearch -v /software/elk/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml -v /software/elk/elasticsearch:/software/elk/elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.7.0
   
   程序的默认配置
   docker run -dti --restart unless-stopped --name elasticsearch -v /software/elk/elasticsearch:/software/elk/elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.7.0

   
   
   
   没有权限执行
   chmod -R 775 /software/elk/elasticsearch
   
   java.lang.IllegalArgumentException: node settings must not contain any index level settings

   
   
   安装elasticsearch的插件elasticsearch-head
   docker run --name elasticsearch-head --link elasticsearch:elasticsearch -p 9100:9100 -d mobz/elasticsearch-head:5



第二步，docker安装kibana   
   docker run --name kibana --link elasticsearch:elasticsearch -p 5601:5601 -d kibana:7.7.0
  
              
             
第三步，
    docker stop logstash && docker rm logstash   
    logstash出现错误No configuration found in the configured sources.   
    
    
第四步，    
    docker run -dti --restart unless-stopped --name logstash \
    -v /software/elk/logstash/config/logstash.conf:/usr/share/logstash/config/logstash-liebe.conf \
    -v /software/elk/logstash/pipeline/:/usr/share/logstash/pipeline \
    -v /software/elk/logstash/patterns:/usr/share/logstash/patterns \
    --link elasticsearch:elasticsearch \
    -p 5044:5044 \
    -p 9600:9600 \
    -e elasticsearch.hosts=http://10.10.10.6:9200 \
    -e xpack.monitoring.enabled=true \
    -e xpack.monitoring.elasticsearch.hosts=http://10.10.10.6:9200 \
    logstash:7.7.0 -f /usr/share/logstash/config/logstash-liebe.conf
    
    
    
     docker run -dti --restart unless-stopped --name logstash \
        -v /software/elk/logstash/pipeline/:/usr/share/logstash/pipeline \
        -v /software/elk/logstash/patterns:/usr/share/logstash/patterns \
        --link elasticsearch:elasticsearch \
        -p 5044:5044 \
        -p 9600:9600 \
        -e elasticsearch.hosts=http://10.10.10.6:9200 \
        -e xpack.monitoring.enabled=true \
        -e xpack.monitoring.elasticsearch.hosts=http://10.10.10.6:9200 \
        logstash:7.7.0
    
                
                
                
                
                
                
docker run -d --name zookeeper -p 2181:2181 -itd wurstmeister/zookeeper:latest

docker run -d --name kafka -p 9092:9092 -e KAFKA_ZOOKEEPER_CONNECT=10.10.10.6:2181 -e ALLOW_PLAINTEXT_LISTENER=yes -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://10.10.10.6:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 wurstmeister/kafka:latest
docker exec zookeeper bin/zkCli.sh ls /brokers/topics

input {
  kafka {
    bootstrap_servers => ["192.168.3.200:9092"]
    client_id => "test"
    group_id => "test"
    auto_offset_reset => "latest"
    consumer_threads => 5
    decorate_events => true
    topics => ["laoqiao"]
    type => "bhy"
    codec => "json"   
    }
}
filter {
  grok {
    match => {"message"=>"%{COMBINEDAPACHELOG}"}
  }
}
output {
  elasticsearch {
    hosts => ["192.168.3.200:9200"]
    index => "yl_test-%{+YYYY.MM.dd}"
    }
}


/opt/kafka/bin/kafka-topics.sh --zookeeper zookeeper:2181 --list
Bash
/opt/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic laoqiao
Bash
/opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic laoqiao --from-beginning
Bash
docker exec zookeeper bin/zkCli.sh ls /brokers/topics


#logstash配置从kafka读取数据输入到 eselasticsearch。（已测试调通）



Bash
input {
  kafka {
    bootstrap_servers => ["192.168.3.200:9092"]
    client_id => "test"
    group_id => "test"
    auto_offset_reset => "latest"
    consumer_threads => 5
    decorate_events => true
    topics => ["laoqiao"]
    type => "bhy"
    codec => "json"   
    }
}
filter {
  grok {
    match => {"message"=>"%{COMBINEDAPACHELOG}"}
  }
}
output {
  elasticsearch {
    hosts => ["192.168.3.200:9200"]
    index => "yl_test-%{+YYYY.MM.dd}"
    }
}


/opt/kafka/bin/kafka-topics.sh --zookeeper zookeeper:2181 --list


/opt/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic laoqiao


/opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic laoqiao --from-beginning
