#!/usr/bin/env bash

docker stop rabbitmq
docker stop events-bc
docker stop announcement-bc
docker stop notification-bc
docker stop member-questions-bc

docker rm rabbitmq
docker rm events-bc
docker rm announcement-bc
docker rm notification-bc
docker rm member-questions-bc