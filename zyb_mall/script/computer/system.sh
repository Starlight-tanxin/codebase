

#ip 39.98.142.99
#user root
#password Xyts12345



## tomcat 目录
cd /www/server/tomcat/

## tomcat webapps目录
cd /www/server/tomcat/webapps

## tomcat 跟踪日志
cd /www/server/tomcat/logs
tailf -n 200 catalina-daemon.out


## 微信支付 ssl 证书目录
mkdir -p "/usr/local/weixin.pay/cert.d/"
