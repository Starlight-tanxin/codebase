#!/bin/bash

yum -y  install deltarpm
curl -fsSL https://get.docker.com/ | sh


## 设置镜像加速域名
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://a5dbftpf.mirror.aliyuncs.com"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker


## 创建所需的映射目录
mkdir -p /etc/docker/volume/

## redis
mkdir -p /etc/docker/volume/redis/data/

## mysql
mkdir -p /etc/docker/volume/mysql/conf.d
mkdir -p /etc/docker/volume/mysql/logs
mkdir -p /etc/docker/volume/mysql/data