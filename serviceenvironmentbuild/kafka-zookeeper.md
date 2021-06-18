docker run -dti --restart unless-stopped --name zookeeper -p 2181:2181 -t wurstmeister/zookeeper


docker run -dti --restart unless-stopped --name kafka \
-p 9092:9092 \
-e KAFKA_BROKER_ID=0 \
-e KAFKA_ZOOKEEPER_CONNECT=10.10.10.6:2181 \
-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://10.10.10.6:9092 \
-e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 \
-e KAFKA_HEAP_OPTS="-Xmx512M -Xms256M" \
-d  wurstmeister/kafka


docker run -dti --restart unless-stopped --name kafka \
-p 9092:9092 \
-e KAFKA_BROKER_ID=0 \
-e KAFKA_ZOOKEEPER_CONNECT=10.10.10.6:2181 \
-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://10.10.10.6:9092 \
-e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 \
-d  wurstmeister/kafka


创建kafka主题
docker exec -it kafka /bin/bash

/opt/kafka/bin/kafka-topics.sh --create --zookeeper 10.10.10.6:2181 --replication-factor 1 --partitions 1 --topic liebe


查看创建的主题
/opt/kafka/bin/kafka-topics.sh --list --zookeeper 10.10.10.6:2181

kafka消息生产者
/opt/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic liebe

kafka消息消费者
/opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic liebe --from-beginning
