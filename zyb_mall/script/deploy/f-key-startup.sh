#!/bin/bash

PATH=/usr/local/spring-service/zyb
PORT=62210
JAR_NAME=zyb

# 脚本执行
LOG_NAME="console-"${PORT}.log

nohup \
  java -jar ${PATH}/${JAR_NAME}.jar \
  --server.port=${PORT} \
  > ${PATH}/${LOG_NAME} 2>&1 & \
  echo $! > ${PATH}/run.pid



