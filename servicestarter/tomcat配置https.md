第一步，命令行运行keytool -genkey -help
-alias <alias>                  要处理的条目的别名
 -keyalg <keyalg>                密钥算法名称
 -keysize <keysize>              密钥位大小
 -sigalg <sigalg>                签名算法名称
 -destalias <destalias>          目标别名
 -dname <dname>                  唯一判别名
 -startdate <startdate>          证书有效期开始日期/时间
 -ext <value>                    X.509 扩展
 -validity <valDays>             有效天数
 -keypass <arg>                  密钥口令
 -keystore <keystore>            密钥库名称
 -storepass <arg>                密钥库口令
 -storetype <storetype>          密钥库类型
 -providername <providername>    提供方名称
 -providerclass <providerclass>  提供方类名
 -providerarg <arg>              提供方参数
 -providerpath <pathlist>        提供方类路径
 -v                              详细输出
 -protected                      通过受保护的机制的口令

第二步，生成命令
   keytool -genkey -alias liebe -keyalg 非对称密钥TomcatForLiebe -keysize 512 -keypass liebe123 -keyalg RSA -keystore liebe.keystore -validity 365 -v
   keytool -importkeystore -srckeystore liebe.keystore -destkeystore liebe.keystore -deststoretype pkcs12
   
第三步，将生成的liebe.keystore方法tomcat根目录下，并执行导入
keytool -export -alias liebe -storepass liebe123 -file liebe.cer -keystore liebe.keystore

第四步，将生成的证书导入到密钥库文件中去
keytool -importcert -keystore client_trust.keystore -file liebe.cer -alias client_trust_server -noprompt

第五步，cert转换成p12
keytool -importkeystore -srckeystore liebe.keystore -destkeystore liebe.p12 -srcalias liebe -destalias liebe -srcstoretype jks -deststoretype pkcs12 -noprompt

第六步，
<Connector port="80" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443"/>

    <Connector port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443" />	
			   
    <Connector port="443" protocol="HTTP/1.1"
               URIEncoding="utf-8"
               connectionTimeout="20000"
               enableLookups="false"
               maxPostSize="10485760"
               maxConnections="10000"
               maxThreads="150" SSLEnabled="true" schema="https"
               secure="true" clientAuth="false" sslProtocol="TLS"
               keystoreFile="D:\cloud\keystore\apache-tomcat-9.0.37\liebe.p12"
               keystoreType="pkcs12"
               keystorePass="liebe123" 
			   truststoreFile="D:\cloud\keystore\apache-tomcat-9.0.37\client_trust.keystore" truststoreType="jks" truststorePass="liebe123"/>
			   
	<Connector port="8443" protocol="HTTP/1.1"
               URIEncoding="utf-8"
               connectionTimeout="20000"
               enableLookups="false"
               maxPostSize="10485760"
               maxConnections="10000"
               maxThreads="150" SSLEnabled="true" schema="https"
               secure="true" clientAuth="false" sslProtocol="TLS"
               keystoreFile="D:\cloud\keystore\apache-tomcat-9.0.37\liebe.p12"
               keystoreType="pkcs12"
               keystorePass="liebe123" 
			   truststoreFile="D:\cloud\keystore\apache-tomcat-9.0.37\client_trust.keystore" truststoreType="jks" truststorePass="liebe123"/>
