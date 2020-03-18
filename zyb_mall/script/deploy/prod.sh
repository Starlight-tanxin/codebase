#!/bin/bash



## TODO
cd /usr/local/tomcat/

./startup.sh



## jar 形式

mkdir -p /usr/local/spring-service/zyb
cd /usr/local/spring-service/zyb

nohup \
  java -jar /usr/local/spring-service/zyb/zyb.jar \
  --server.port=62210 \
  > /usr/local/spring-service/zyb/console-62210.log 2>&1 &

tailf -n 200 /usr/local/spring-service/zyb/console-62210.log

ps -ef | grep  "java -jar /usr/local/spring-service/zyb/zyb.jar"