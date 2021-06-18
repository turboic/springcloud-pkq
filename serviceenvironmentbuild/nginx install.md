./configure  --prefix=/usr/local/nginx --sbin-path=/usr/local/nginx/sbin/nginx --conf-path=/usr/local/nginx/conf/nginx.conf --error-log-path=/var/log/nginx/error.log --http-log-path=/var/log/nginx/access.log --pid-path=/usr/local/nginx/pid/nginx.pid --lock-path=/usr/local/nginx/pid/nginx.lock --user=nginx --group=nginx --with-http_ssl_module --with-http_stub_status_module --with-http_gzip_static_module --http-client-body-temp-path=/usr/local/nginx/client/ --http-proxy-temp-path=/usr/local/nginx/proxy/ --http-fastcgi-temp-path=/usr/local/nginx/fcgi/ --http-uwsgi-temp-path=/usr/local/nginx/uwsgi --http-scgi-temp-path=/usr/local/nginx/scgi --with-pcre --with-http_image_filter_module
 
 
 
 
 
 --add-module=/usr/local/nginx-module-vts
 
 
 
 
 /usr/local/nginx/sbin/nginx -c /usr/local/nginx/conf/nginx.conf
 useradd -s /sbin/nologin -M nginx
 id nginx
 
 
 
 nginx json格式化
 nginx.conf
 
 重新加载配置文件
 /usr/local/nginx/sbin/nginx -s reload
 
 
 
 
 /usr/local/nginx/sbin/nginx -c /usr/local/nginx/conf/nginx.conf