#!/usr/bin/env bash

docker pull knbiteventsbc/events-bc:0.1
docker pull knbiteventsbc/announcement-bc:0.1
docker pull knbiteventsbc/notification-bc:0.1

docker run -d --name rabbitmq rabbitmq
docker run -d --name events-bc --link rabbitmq:rabbitmq -p 9090:8080 knbiteventsbc/events-bc:0.1
docker run -d --name announcement-bc -p 9091:8081 knbiteventsbc/announcement-bc:0.1
docker run -d --name notification-bc --link rabbitmq:rabbitmq -p 9092:8082 knbiteventsbc/notification-bc:0.1
docker run -d --name member-questions-bc --link rabbitmq:rabbitmq -p 9093:8083 knbiteventsbc/member-questions-bc:0.1
