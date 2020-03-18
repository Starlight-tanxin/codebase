#!/bin/bash



## TODO
cd /usr/local/tomcat/

./startup.sh



## jar 形式

mkdir -p /usr/local/spring-service/zyb
cd /usr/local/spring-service/zyb

nohup java -jar zyb.jar --server.port=62210 &

tailf -n 200 /usr/local/spring-service/zyb/nohup.out


ps -ef | grep  "java -jar zyb.jar --server.port=62210"
