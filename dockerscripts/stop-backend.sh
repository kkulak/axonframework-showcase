#!/usr/bin/env bash

docker stop rabbitmq
docker stop events-bc
docker stop announcement-bc -p 9091:8081
docker stop notification-bc -p 9092:8082

docker rm rabbitmq
docker rm events-bc
docker rm announcement-bc -p 9091:8081
docker rm notification-bc -p 9092:8082