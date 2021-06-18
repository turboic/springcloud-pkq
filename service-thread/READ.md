kafka远程客户端无法访问server
查看监听端口，是否允许远程访问
sudo netstat -apn|grep 'LISTEN '|grep 9092
tcp6       0      0 127.0.0.1:9092          :::*                    LISTEN      5584/java   

上面的只能是本地的访问

listeners=PLAINTEXT://:9092

# Hostname and port the broker will advertise to producers and consumers. If not set,
# it uses the value for "listeners" if configured.  Otherwise, it will use the value
# returned from java.net.InetAddress.getCanonicalHostName().
advertised.listeners=PLAINTEXT://10.10.10.12:9092
