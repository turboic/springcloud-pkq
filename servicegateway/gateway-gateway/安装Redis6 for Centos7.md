一、安装gcc依赖

由于 redis 是用 C 语言开发，安装之前必先确认是否安装 gcc 环境（gcc -v），如果没有安装，执行以下命令进行安装
yum install gcc
# 查看gcc版本是否在5.3以上，centos7.6默认安装4.8.5
gcc -v
# 升级gcc到5.3及以上,如下：
升级到gcc 9.3：
yum -y install centos-release-scl
yum -y install devtoolset-9-gcc devtoolset-9-gcc-c++ devtoolset-9-binutils
scl enable devtoolset-9 bash
需要注意的是scl命令启用只是临时的，退出shell或重启就会恢复原系统gcc版本。
如果要长期使用gcc 9.3的话：

echo "source /opt/rh/devtoolset-9/enable" >>/etc/profile

出现错误从新打开shell窗口，设置上面的环境变量生效，注意，针对特定的版本哈哈哈


清理redis编译缓存
make distclean

二、下载并解压安装包

使用迅雷下载Redis安装文件
http://download.redis.io/releases/redis-6.0.4.tar.gz

通过xftp等工具上传到centos7目录下

[root@localhost local]# tar -zxvf redis-6.0.4.tar.gz

 

三、cd切换到redis解压目录下，执行编译

[root@localhost local]# cd redis-6.0.4

[root@localhost redis-5.0.3]# make

 

四、安装并指定安装目录

[root@localhost redis-5.0.3]# make install PREFIX=/usr/local/redis

 

五、启动服务

5.1前台启动

[root@localhost redis-5.0.3]# cd /usr/local/redis/bin/

[root@localhost bin]# ./redis-server

 

5.2后台启动

从 redis 的源码目录中复制 redis.conf 到 redis 的安装目录

[root@localhost bin]# cp /software/redis-6.0.4/redis.conf /usr/local/redis/bin/

 

修改 redis.conf 文件，把 daemonize no 改为 daemonize yes

[root@localhost bin]# vim redis.conf



后台启动

[root@localhost bin]# ./redis-server redis.conf



 

六、设置开机启动

添加开机启动服务

[root@localhost bin]# vim /etc/systemd/system/redis.service

复制粘贴以下内容：

复制代码
[Unit]
Description=redis-server
After=network.target

[Service]
Type=forking
ExecStart=/usr/local/redis/bin/redis-server /usr/local/redis/bin/redis.conf
PrivateTmp=true

[Install]
WantedBy=multi-user.target

复制代码
注意：ExecStart配置成自己的路径 

 

设置开机启动

[root@localhost bin]# systemctl daemon-reload

[root@localhost bin]# systemctl start redis.service

[root@localhost bin]# systemctl enable redis.service

 

创建 redis 命令软链接

[root@localhost ~]# ln -s /usr/local/redis/bin/redis-cli /usr/bin/redis

测试 redis



 

服务操作命令

systemctl start redis.service   #启动redis服务

systemctl stop redis.service   #停止redis服务

systemctl restart redis.service   #重新启动服务

systemctl status redis.service   #查看服务当前状态

systemctl enable redis.service   #设置开机自启动

systemctl disable redis.service   #停止开机自启动