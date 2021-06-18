2020-04-10 加班  没休
2020-05-10 的加班 2020-05-11已调休
2020-05-16 加班  没休
2020-05-17 加班  没休
2020-05-23 加班  没休


创建主题

[root@localhost kafka1]# ./bin/kafka-topics.sh --create --zookeeper localhost:2181,localhost:2182,localhost:2183 --replication-factor 3 --partitions 3 --topic liebe
Created topic liebe.

docker run -dti --restart unless-stopped --name rmqconsole --link rmqnamesrv:rmqnamesrv \
-e "JAVA_OPTS=-Drocketmq.namesrv.addr=rmqnamesrv:9876 -Dcom.rocketmq.sendMessageWithVIPChannel=false" \
-p 8180:8080 -t styletang/rocketmq-console-ng


docker run -dti --restart unless-stopped  -v /software/rocketmq/logs:/home/rocketmq/logs -v /software/rocketmq/store:/home/rocketmq/store \
      --name rmqbroker \
      -e "NAMESRV_ADDR=rmqnamesrv:9876" \
      -e "JAVA_OPT_EXT=-Xms512M -Xmx512M -Xmn128m" \
      -p 10911:10911 -p 10912:10912 -p 10909:10909 \
      foxiswho/rocketmq:4.7.0 sh mqbroker

docker run -dti --restart unless-stopped -p 9876:9876 -v /software/rocketmq/namesrv/logs:/root/logs \
-v /software/rocketmq/namesrv/store:/root/store --name rmqnamesrv \
-e "MAX_POSSIBLE_HEAP=100000000" rocketmqinc/rocketmq sh mqnamesrv

docker run -dti --restart unless-stopped -p 10911:10911 -p 10909:10909 \
-v  /software/rocketmq/broker/logs:/root/logs \
-v  /software/rocketmq/broker/store:/root/store \
-v  /software/rocketmq/broker/conf/broker.conf:/etc/rocketmq/conf/broker.conf \
--name rmqbroker --link rmqnamesrv:namesrv -e "NAMESRV_ADDR=namesrv:9876" -e "MAX_POSSIBLE_HEAP=200000000" \
rocketmqinc/rocketmq sh mqbroker -c /etc/rocketmq/conf/broker.conf



docker run -dti --restart unless-stopped -e "JAVA_OPTS=-Drocketmq.namesrv.addr=10.10.10.6:9876 -Dcom.rocketmq.sendMessageWithVIPChannel=false" -p 8280:8080 -t pangliang/rocketmq-console-ng



su - oracle

/u01/app/oracle/product/11.2.0/dbhome_1/bin

./sqlplus / as sysdba


ORA-00845: MEMORY_TARGET not supported on this system

root权限下执行   mount -o remount,size=16G /dev/shm


alter database datafile '/u01/app/db.dbf' offline;

alter database recover datafile '/u01/app/db.dbf'

alter database datafile '/u01/app/db.dbf' online;


recover datafile '/u01/app/bjqb2.dbf'


recover database until change 2804404 using backup controlfile;

