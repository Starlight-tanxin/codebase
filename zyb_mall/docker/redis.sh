#!/bin/bash

## 拉取镜像
docker pull redis:5.0.6

## 运行镜像
docker run \
  --name redis \
  --hostname=redis \
  --restart=always \
  -d \
  -p 63221:6379 \
  -v /etc/docker/volume/redis/data:/data \
  redis:5.0.6 \
  redis-server \
  --appendonly yes \
  --requirepass "OnlinezuozuoCreated.redis.zyb"


## 删除该容器
docker container stop redis
docker container rm redis


## 进入面板
docker exec -it redis redis-cli
auth OnlinezuozuoCreated.redis.zyb
keys *


## 查看运行状态
docker ps | grep "redis"

## 设置时区
ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone
date "+%Y-%m-%d %H:%M:%S" ## 显示设置后，新的系统时间