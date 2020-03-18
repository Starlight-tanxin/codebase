#!/bin/bash


## 拉取镜像
docker pull mysql:8.0.18

## 运行镜像
docker run \
  --name mysql \
  --hostname=mysql \
  --restart=always \
  -d \
  -p 33221:3306 \
  -v /etc/docker/volume/mysql/conf.d:/conf.d \
  -v /etc/docker/volume/mysql/logs:/logs \
  -v /etc/docker/volume/mysql/data:/var/lib/mysql \
  -e MYSQL_ROOT_PASSWORD="OnlinezuozuoCreated.mysql.zyb" \
  mysql:8.0.18


## 删除该容器
docker container stop mysql
docker container rm mysql


## 进入面板
docker exec -it mysql mysql -u root -p # OnlinezuozuoCreated.mysql.zyb

## 查看运行状态
docker ps | grep "mysql"


## 设置时区
ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone
date "+%Y-%m-%d %H:%M:%S" ## 显示设置后，新的系统时间

## 授权
use mysql

select user ,host from user;

update user set host="%" where user="root";

flush privileges;